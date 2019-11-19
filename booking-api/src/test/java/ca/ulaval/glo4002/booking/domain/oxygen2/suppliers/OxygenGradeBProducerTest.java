package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeBSettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenSupplySettings;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class OxygenGradeBProducerTest {

    private static final LocalDate ORDER_DATE = LocalDate.now();
    private static final int SOME_QUANTITY = 26;
    private static final OxygenSupplySettings SUPPLY_SETTINGS = new OxygenGradeBSettings();
    private static final LocalDate RECEIVED_DATE = ORDER_DATE.plusDays(SUPPLY_SETTINGS.getNumberOfDaysToReceive());
    private static final int LITERS_OF_WATER_PER_BATCH = 8;

    private OxygenGradeBProducer oxygenGradeBProducer;
    private OxygenInventoryEntry oxygenInventoryEntry;
    private OxygenHistory oxygenHistory;
    private OutcomeSaver outcomeSaver;
    private OxygenHistoryEntry orderDateOxygenHistoryEntry;
    private OxygenHistoryEntry receivedDateOxygenHistoryEntry;

    @BeforeEach
    public void setup() {
        oxygenInventoryEntry = mock(OxygenInventoryEntry.class);
        oxygenHistory = mock(OxygenHistory.class);
        outcomeSaver = mock(OutcomeSaver.class);
        oxygenGradeBProducer = new OxygenGradeBProducer(oxygenHistory, outcomeSaver);

        orderDateOxygenHistoryEntry = mock(OxygenHistoryEntry.class);
        when(oxygenHistory.findOrCreate(ORDER_DATE)).thenReturn(orderDateOxygenHistoryEntry);
        receivedDateOxygenHistoryEntry = mock(OxygenHistoryEntry.class);
        when(oxygenHistory.findOrCreate(RECEIVED_DATE)).thenReturn(receivedDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheProducedQuantityToTheInventory() {
        oxygenGradeBProducer.supply(ORDER_DATE, SOME_QUANTITY, oxygenInventoryEntry);
        int quantityProduced = OxygenSupplierTestHelper.getQuantityProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        verify(oxygenInventoryEntry).addQuantity(quantityProduced);
    }

    @Test
    public void whenSupplying_itAddsTheProducedQuantityToTheHistoryAtTheReceivedDateAndSaves() {
        oxygenGradeBProducer.supply(ORDER_DATE, SOME_QUANTITY, oxygenInventoryEntry);
        int quantityProduced = OxygenSupplierTestHelper.getQuantityProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        verify(receivedDateOxygenHistoryEntry).addTankMade(quantityProduced);
        verify(oxygenHistory).save(receivedDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheWaterUsedToTheHistoryAtTheOrderDateAndSaves() {
        oxygenGradeBProducer.supply(ORDER_DATE, SOME_QUANTITY, oxygenInventoryEntry);
        int numberOfBatchesProduced = OxygenSupplierTestHelper.getNumberOfBatchesProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        int candlesUsed = numberOfBatchesProduced * LITERS_OF_WATER_PER_BATCH;
        verify(orderDateOxygenHistoryEntry).addWaterUsed(candlesUsed);
        verify(oxygenHistory).save(orderDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheOutcomeToTheProfitCalculator() {
        oxygenGradeBProducer.supply(ORDER_DATE, SOME_QUANTITY, oxygenInventoryEntry);
        int numberOfBatchesProduced = OxygenSupplierTestHelper.getNumberOfBatchesProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        Price cost = SUPPLY_SETTINGS.getCostPerBatch().multipliedBy(numberOfBatchesProduced);
        verify(outcomeSaver).saveOutcome(cost);
    }
}
