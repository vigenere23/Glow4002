package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OxygenProducerTests {
    
    private OxygenProducer oxygenProducer;
    private OxygenProductionResults results;
    private final static OffsetDateTime festivalStartingDate = OffsetDateTime.of(2050, 7, 17, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime oneMonthBeforeFestivalDate = OffsetDateTime.of(2050, 6, 17, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime completionDateGradeAOrder = OffsetDateTime.of(2050, 7, 7, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime fifteenDaysBeforeFestivalDate = OffsetDateTime.of(2050, 7, 2, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime fiveDaysBeforeFestivalDate = OffsetDateTime.of(2050, 7, 12, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime duringFestival = OffsetDateTime.of(2050, 7, 18, 0, 0, 0, 0, ZoneOffset.UTC);

    @BeforeEach
    public void testInitialize() {
        oxygenProducer = new OxygenProducer(festivalStartingDate);
        results = new OxygenProductionResults();
        results.orderDateHistory = new History();
        results.orderDateHistory.date = oneMonthBeforeFestivalDate;
        results.deliveryDateHistory = new History();
        results.deliveryDateHistory.date = completionDateGradeAOrder;
    }

    @Test
    public void whenQuantityToProduceIsFiveAndRemainingIsThree_thenTotalToProduceIsTwo() {
        assertEquals(2, oxygenProducer.calculateTotalToProduce(5, 3));
    }

    @Test
    public void whenOrderOxygenQuantityLessThanMinimumFabricationQuantity_thenMinimumFabricationQuantityIsAdded() {
        oxygenProducer.produceOxygen(OxygenGrade.A, 3, results);
        assertEquals(5, results.quantityTankToAddToInventory);
    }

    @Test
    public void whenOrderOxygenQuantityLessThanMinimumFabricationQuantity_thenDeliveryDatQtyOxygenTankMadeHistoryIsUpdated() {
        oxygenProducer.produceOxygen(OxygenGrade.A, 3, results);
        assertEquals(5, results.deliveryDateHistory.qtyOxygenTankMade);
    }

    @Test
    public void whenOrderOxygenQuantityLessThanMinimumFabricationQuantity_thenOrderDateQtyCandlesUsedHistoryIsUpdated() {
        oxygenProducer.produceOxygen(OxygenGrade.A, 3, results);
        assertEquals(15, results.orderDateHistory.qtyCandlesUsed);
    }

    @Test
    public void whenOrderOxygenQuantityMoreThanMinimumFabricationQuantity_thenMultipleOfMinimumFabricationQuantityIsAdded() {
        oxygenProducer.produceOxygen(OxygenGrade.A, 6, results);
        assertEquals(10, results.quantityTankToAddToInventory);
    }

    @Test
    public void whenOrderOxygenQuantityMoreThanMinimumFabricationQuantity_thenDeliveryDatQtyOxygenTankMadeHistoryIsUpdated() {
        oxygenProducer.produceOxygen(OxygenGrade.A, 6, results);
        assertEquals(10, results.deliveryDateHistory.qtyOxygenTankMade);
    }

    @Test
    public void whenOrderOxygenQuantityMoreThanMinimumFabricationQuantity_thenOrderDateQtyCandlesUsedHistoryIsUpdated() {
        oxygenProducer.produceOxygen(OxygenGrade.A, 6, results);
        assertEquals(30, results.orderDateHistory.qtyCandlesUsed);
    }

    @Test
    public void whenOrderOxygenQuantityGradeB_thenOrderDateQtyWaterUsedHistoryIsUpdated() {
        oxygenProducer.produceOxygen(OxygenGrade.B, 2, results);
        assertEquals(8, results.orderDateHistory.qtyWaterUsed);
    }

    @Test
    public void whenOrderOxygenQuantityGradeE_thenOrderDateQtyOxygenTankBoughtHistoryIsUpdated() {
        oxygenProducer.produceOxygen(OxygenGrade.E, 2, results);
        assertEquals(2, results.orderDateHistory.qtyOxygenTankBought);
    }

    @Test
    public void whenOrderOxygenGradeATooLateForGradeA_thenRealGradeToProduceIsGradeB() {
        OxygenGrade realGradeToProduce = oxygenProducer.getRealGradeToProduce(fifteenDaysBeforeFestivalDate, OxygenGrade.A);
        assertEquals(OxygenGrade.B, realGradeToProduce);
    }

    @Test
    public void whenOrderOxygenGradeATooLateForGradesAandB_thenRealGradeToProduceIsGradeE() {
        OxygenGrade realGradeToProduce = oxygenProducer.getRealGradeToProduce(fiveDaysBeforeFestivalDate, OxygenGrade.A);
        assertEquals(OxygenGrade.E, realGradeToProduce);
    }

    @Test
    public void whenOrderOxygenGradeAOnLimitDeliveryDate_thenRealGradeToProduceIsGradeE() {
        OxygenGrade realGradeToProduce = oxygenProducer.getRealGradeToProduce(festivalStartingDate, OxygenGrade.A);
        assertEquals(OxygenGrade.E, realGradeToProduce);
    }

    @Test
    public void whenOrderOxygenGradeAAfterLimitDeliveryDate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> oxygenProducer.getRealGradeToProduce(duringFestival, OxygenGrade.A));
    }
}