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
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenGradeESettings;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenSupplySettings;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class OxygenGradeEBuyerTest {

    private static final LocalDate ORDER_DATE = LocalDate.now();
    private static final int SOME_QUANTITY = 26;
    private static final OxygenSupplySettings SUPPLY_SETTINGS = new OxygenGradeESettings();
    private static final LocalDate RECEIVED_DATE = ORDER_DATE.plusDays(SUPPLY_SETTINGS.getNumberOfDaysToReceive());

    private OxygenGradeEBuyer oxygenGradeEBuyer;
    private OxygenInventoryEntry oxygenInventoryEntry;
    private InMemoryOxygenHistoryRepository oxygenHistory;
    private OutcomeSaver outcomeSaver;
    private OxygenHistoryEntry receivedDateOxygenHistoryEntry;

    @BeforeEach
    public void setup() {
        oxygenInventoryEntry = mock(OxygenInventoryEntry.class);
        oxygenHistory = mock(InMemoryOxygenHistoryRepository.class);
        outcomeSaver = mock(OutcomeSaver.class);
        oxygenGradeEBuyer = new OxygenGradeEBuyer(oxygenHistory, outcomeSaver);

        receivedDateOxygenHistoryEntry = mock(OxygenHistoryEntry.class);
        when(oxygenHistory.findOrCreate(RECEIVED_DATE)).thenReturn(receivedDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheProducedQuantityToTheInventory() {
        oxygenGradeEBuyer.supply(ORDER_DATE, SOME_QUANTITY, oxygenInventoryEntry);
        int quantityProduced = OxygenSupplierTestHelper.getQuantityProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        verify(oxygenInventoryEntry).addQuantity(quantityProduced);
    }

    @Test
    public void whenSupplying_itAddsTheBoughtQuantityToTheHistoryAtTheReceivedDateAndSaves() {
        oxygenGradeEBuyer.supply(ORDER_DATE, SOME_QUANTITY, oxygenInventoryEntry);
        int quantityProduced = OxygenSupplierTestHelper.getQuantityProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        verify(receivedDateOxygenHistoryEntry).addTankBought(quantityProduced);
        verify(oxygenHistory).add(receivedDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheOutcomeToTheOutcomeSaver() {
        oxygenGradeEBuyer.supply(ORDER_DATE, SOME_QUANTITY, oxygenInventoryEntry);
        int numberOfBatchesProduced = OxygenSupplierTestHelper.getNumberOfBatchesProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        Price cost = SUPPLY_SETTINGS.getCostPerBatch().multipliedBy(numberOfBatchesProduced);
        verify(outcomeSaver).addOutcome(cost);
    }
}
