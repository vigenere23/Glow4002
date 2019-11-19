package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.finance.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenGradeESettings;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenSupplySettings;

public class OxygenGradeEBuyerTest {

    private static final LocalDate ORDER_DATE = LocalDate.now();
    private static final int SOME_QUANTITY = 26;
    private static final OxygenSupplySettings SUPPLY_SETTINGS = new OxygenGradeESettings();
    private static final LocalDate RECEIVED_DATE = ORDER_DATE.plusDays(SUPPLY_SETTINGS.getNumberOfDaysToReceive());

    private OxygenGradeEBuyer oxygenGradeEBuyer;
    private OxygenInventory oxygenInventory;
    private OxygenHistory oxygenHistory;
    private ProfitCalculator profitCalculator;
    private OxygenHistoryEntry receivedDateOxygenHistoryEntry;

    @BeforeEach
    public void setup() {
        oxygenInventory = mock(OxygenInventory.class);
        oxygenHistory = mock(OxygenHistory.class);
        profitCalculator = mock(ProfitCalculator.class);
        oxygenGradeEBuyer = new OxygenGradeEBuyer(oxygenInventory, oxygenHistory, profitCalculator);

        receivedDateOxygenHistoryEntry = mock(OxygenHistoryEntry.class);
        when(oxygenHistory.findOrCreate(RECEIVED_DATE)).thenReturn(receivedDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheProducedQuantityToTheInventory() {
        oxygenGradeEBuyer.supply(ORDER_DATE, SOME_QUANTITY);
        int quantityProduced = OxygenSupplierTestHelper.getQuantityProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        verify(oxygenInventory).addQuantity(SUPPLY_SETTINGS.getGrade(), quantityProduced);
    }

    @Test
    public void whenSupplying_itAddsTheBoughtQuantityToTheHistoryAtTheReceivedDateAndSaves() {
        oxygenGradeEBuyer.supply(ORDER_DATE, SOME_QUANTITY);
        int quantityProduced = OxygenSupplierTestHelper.getQuantityProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        verify(receivedDateOxygenHistoryEntry).addTankBought(quantityProduced);
        verify(oxygenHistory).save(receivedDateOxygenHistoryEntry);
    }

    @Test
    public void whenSupplying_itAddsTheOutcomeToTheProfitCalculator() {
        oxygenGradeEBuyer.supply(ORDER_DATE, SOME_QUANTITY);
        int numberOfBatchesProduced = OxygenSupplierTestHelper.getNumberOfBatchesProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        Price cost = SUPPLY_SETTINGS.getCostPerBatch().multipliedBy(numberOfBatchesProduced);
        verify(profitCalculator).addOutcome(cost);
    }
}
