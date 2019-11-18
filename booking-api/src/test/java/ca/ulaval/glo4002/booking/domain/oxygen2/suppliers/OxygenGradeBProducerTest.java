package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.finance.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
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

    @BeforeEach
    public void setup() {
        oxygenInventory = mock(OxygenInventory.class);
        oxygenHistory = mock(OxygenHistory.class);
        profitCalculator = mock(ProfitCalculator.class);
        oxygenGradeBProducer = new OxygenGradeBProducer(oxygenInventory, oxygenHistory, profitCalculator);
    }

    @Test
    public void whenSupplying_itAddsTheProducedQuantityToTheInventory() {
        oxygenGradeBProducer.supply(ORDER_DATE, SOME_QUANTITY);
        int quantityProduced = OxygenSupplierTestHelper.getQuantityProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        verify(oxygenInventory).addQuantity(SUPPLY_SETTINGS.getGrade(), quantityProduced);
    }

    @Test
    public void whenSupplying_itAddsTheProducedQuantityToTheHistoryAtTheReceivedDate() {
        oxygenGradeBProducer.supply(ORDER_DATE, SOME_QUANTITY);
        int quantityProduced = OxygenSupplierTestHelper.getQuantityProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        verify(oxygenHistory).addTankMade(RECEIVED_DATE, quantityProduced);
    }

    @Test
    public void whenSupplying_itAddsTheWaterUsedToTheHistoryAtTheOrderDate() {
        oxygenGradeBProducer.supply(ORDER_DATE, SOME_QUANTITY);
        int numberOfBatchesProduced = OxygenSupplierTestHelper.getNumberOfBatchesProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        int candlesUsed = numberOfBatchesProduced * LITERS_OF_WATER_PER_BATCH;
        verify(oxygenHistory).addWaterUsed(ORDER_DATE, candlesUsed);
    }

    @Test
    public void whenSupplying_itAddsTheOutcomeToTheProfitCalculator() {
        oxygenGradeBProducer.supply(ORDER_DATE, SOME_QUANTITY);
        int numberOfBatchesProduced = OxygenSupplierTestHelper.getNumberOfBatchesProduced(SOME_QUANTITY, SUPPLY_SETTINGS);
        Price cost = SUPPLY_SETTINGS.getCostPerBatch().multipliedBy(numberOfBatchesProduced);
        verify(profitCalculator).addOutcome(cost);
    }
}
