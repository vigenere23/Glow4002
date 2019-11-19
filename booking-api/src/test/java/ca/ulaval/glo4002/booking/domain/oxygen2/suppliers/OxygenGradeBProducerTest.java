package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.finance.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeBSettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenSupplySettings;

public class OxygenGradeBProducerTest {

    private static final LocalDate ORDER_DATE = LocalDate.now();
    private static final int SOME_QUANTITY = 26;
    private static final OxygenSupplySettings SUPPLY_SETTINGS = new OxygenGradeBSettings();
    private static final LocalDate RECEIVED_DATE = ORDER_DATE.plusDays(SUPPLY_SETTINGS.getNumberOfDaysToReceive());
    private static final int LITERS_OF_WATER_PER_BATCH = 8;

    private OxygenGradeBProducer oxygenGradeBProducer;
    private OxygenInventory oxygenInventory;
    private OxygenHistory oxygenHistory;
    private ProfitCalculator profitCalculator;
    private OxygenHistoryEntry orderDateOxygenHistoryEntry;
    private OxygenHistoryEntry receivedDateOxygenHistoryEntry;

    @BeforeEach
    public void setup() {
        oxygenInventory = mock(OxygenInventory.class);
        oxygenHistory = mock(OxygenHistory.class);
        profitCalculator = mock(ProfitCalculator.class);
        oxygenGradeBProducer = new OxygenGradeBProducer(oxygenInventory, oxygenHistory, profitCalculator);

        orderDateOxygenHistoryEntry = mock(OxygenHistoryEntry.class);
        when(oxygenHistory.findOrCreate(ORDER_DATE)).thenReturn(orderDateOxygenHistoryEntry);
        receivedDateOxygenHistoryEntry = mock(OxygenHistoryEntry.class);
        when(oxygenHistory.findOrCreate(RECEIVED_DATE)).thenReturn(receivedDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheProducedQuantityToTheInventory() {
        oxygenGradeBProducer.supply(ORDER_DATE, SOME_QUANTITY);
        int quantityProduced = OxygenSupplierTestHelper.getQuantityProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        verify(oxygenInventory).addQuantity(SUPPLY_SETTINGS.getGrade(), quantityProduced);
    }

    @Test
    public void whenSupplying_itAddsTheProducedQuantityToTheHistoryAtTheReceivedDateAndSaves() {
        oxygenGradeBProducer.supply(ORDER_DATE, SOME_QUANTITY);
        int quantityProduced = OxygenSupplierTestHelper.getQuantityProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        verify(receivedDateOxygenHistoryEntry).addTankMade(quantityProduced);
        verify(oxygenHistory).save(receivedDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheWaterUsedToTheHistoryAtTheOrderDateAndSaves() {
        oxygenGradeBProducer.supply(ORDER_DATE, SOME_QUANTITY);
        int numberOfBatchesProduced = OxygenSupplierTestHelper.getNumberOfBatchesProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        int candlesUsed = numberOfBatchesProduced * LITERS_OF_WATER_PER_BATCH;
        verify(orderDateOxygenHistoryEntry).addWaterUsed(candlesUsed);
        verify(oxygenHistory).save(orderDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheOutcomeToTheProfitCalculator() {
        oxygenGradeBProducer.supply(ORDER_DATE, SOME_QUANTITY);
        int numberOfBatchesProduced = OxygenSupplierTestHelper.getNumberOfBatchesProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        Price cost = SUPPLY_SETTINGS.getCostPerBatch().multipliedBy(numberOfBatchesProduced);
        verify(profitCalculator).addOutcome(cost);
    }
}
