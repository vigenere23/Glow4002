package ca.ulaval.glo4002.booking.domain.oxygen.suppliers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.infrastructure.persistence.memory.InMemoryOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenGradeASettings;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenSupplySettings;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class OxygenGradeAProducerTest {

    private static final LocalDate ORDER_DATE = LocalDate.now();
    private static final int SOME_QUANTITY = 26;
    private static final OxygenSupplySettings SUPPLY_SETTINGS = new OxygenGradeASettings();
    private static final LocalDate RECEIVED_DATE = ORDER_DATE.plusDays(SUPPLY_SETTINGS.getNumberOfDaysToReceive());
    private static final int NUMBER_OF_CANDLES_PER_BATCH = 15;

    private OxygenGradeAProducer oxygenGradeAProducer;
    private OxygenInventoryEntry oxygenInventoryEntry;
    private InMemoryOxygenHistoryRepository oxygenHistory;
    private OutcomeSaver outcomeSaver;
    private OxygenHistoryEntry orderDateOxygenHistoryEntry;
    private OxygenHistoryEntry receivedDateOxygenHistoryEntry;

    @BeforeEach
    public void setup() {
        oxygenInventoryEntry = mock(OxygenInventoryEntry.class);
        oxygenHistory = mock(InMemoryOxygenHistoryRepository.class);
        outcomeSaver = mock(OutcomeSaver.class);
        oxygenGradeAProducer = new OxygenGradeAProducer(oxygenHistory, outcomeSaver);

        orderDateOxygenHistoryEntry = mock(OxygenHistoryEntry.class);
        when(oxygenHistory.findOrCreate(ORDER_DATE)).thenReturn(orderDateOxygenHistoryEntry);
        receivedDateOxygenHistoryEntry = mock(OxygenHistoryEntry.class);
        when(oxygenHistory.findOrCreate(RECEIVED_DATE)).thenReturn(receivedDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheProducedQuantityToTheInventory() {
        oxygenGradeAProducer.supply(ORDER_DATE, SOME_QUANTITY, oxygenInventoryEntry);
        int quantityProduced = OxygenSupplierTestHelper.getQuantityProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        verify(oxygenInventoryEntry).addQuantity(quantityProduced);
    }

    @Test
    public void whenSupplying_itAddsTheProducedQuantityToTheHistoryAtTheReceivedDateAndSaves() {
        oxygenGradeAProducer.supply(ORDER_DATE, SOME_QUANTITY, oxygenInventoryEntry);
        int quantityProduced = OxygenSupplierTestHelper.getQuantityProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        verify(receivedDateOxygenHistoryEntry).addTankMade(quantityProduced);
        verify(oxygenHistory).save(receivedDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheUsedCandlesToTheHistoryAtTheOrderDateAndSaves() {
        oxygenGradeAProducer.supply(ORDER_DATE, SOME_QUANTITY, oxygenInventoryEntry);
        int numberOfBatchesProduced = OxygenSupplierTestHelper.getNumberOfBatchesProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        int candlesUsed = numberOfBatchesProduced * NUMBER_OF_CANDLES_PER_BATCH;
        verify(orderDateOxygenHistoryEntry).addCandlesUsed(candlesUsed);
        verify(oxygenHistory).save(orderDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheOutcomeToTheProfitCalculator() {
        oxygenGradeAProducer.supply(ORDER_DATE, SOME_QUANTITY, oxygenInventoryEntry);
        int numberOfBatchesProduced = OxygenSupplierTestHelper.getNumberOfBatchesProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        Price cost = SUPPLY_SETTINGS.getCostPerBatch().multipliedBy(numberOfBatchesProduced);
        verify(outcomeSaver).saveOutcome(cost);
    }
}
