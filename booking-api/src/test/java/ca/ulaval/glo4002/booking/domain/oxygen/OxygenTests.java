package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OxygenTests {

    private OxygenProducer2 oxygenProducer;
    private OxygenProductionResults results;
    private final static LocalDate FESTIVAL_STARTING_DATE = LocalDate.of(2050, 7, 17);
    private final static LocalDate ONE_MONTH_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusMonths(1);
    private final static LocalDate COMPLETION_DATE_GRADE_A_ORDER = LocalDate.of(2050, 7, 7);
    private final static LocalDate FIFTEEN_DAYS_BEFORE_FESTIVAL = FESTIVAL_STARTING_DATE.minusDays(15);
    private final static LocalDate FIVE_DAYS_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusDays(5);
    private final static LocalDate DURING_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.plusDays(1);

    @BeforeEach
    public void testInitialize() {
        oxygenProducer = new OxygenProducer2(FESTIVAL_STARTING_DATE);
        results = new OxygenProductionResults();
        results.orderDateHistory = new History();
        results.orderDateHistory.date = ONE_MONTH_BEFORE_FESTIVAL_DATE;
        results.deliveryDateHistory = new History();
        results.deliveryDateHistory.date = COMPLETION_DATE_GRADE_A_ORDER;
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
    public void givenExistingOxygenTankMadeQty_whenOrderOxygenQuantityLessThanMinimumFabricationQuantity_thenDeliveryDatQtyOxygenTankMadeHistoryIsUpdated() {
        results.deliveryDateHistory.qtyOxygenTankMade = 2;
        oxygenProducer.produceOxygen(OxygenGrade.A, 3, results);
        assertEquals(7, results.deliveryDateHistory.qtyOxygenTankMade);
    }

    @Test
    public void whenOrderOxygenQuantityLessThanMinimumFabricationQuantity_thenOrderDateQtyCandlesUsedHistoryIsUpdated() {
        oxygenProducer.produceOxygen(OxygenGrade.A, 3, results);
        assertEquals(15, results.orderDateHistory.qtyCandlesUsed);
    }

    @Test
    public void givenExistingCandlesUsed_whenOrderOxygenQuantityLessThanMinimumFabricationQuantity_thenOrderDateQtyCandlesUsedHistoryIsUpdated() {
        results.orderDateHistory.qtyCandlesUsed = 2;
        oxygenProducer.produceOxygen(OxygenGrade.A, 3, results);
        assertEquals(17, results.orderDateHistory.qtyCandlesUsed);
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
    public void givenExistingWaterUsedQty_whenOrderOxygenQuantityGradeB_thenOrderDateQtyWaterUsedHistoryIsUpdated() {
        results.orderDateHistory.qtyWaterUsed = 2;
        oxygenProducer.produceOxygen(OxygenGrade.B, 2, results);
        assertEquals(10, results.orderDateHistory.qtyWaterUsed);
    }

    @Test
    public void whenOrderOxygenQuantityGradeE_thenOrderDateQtyOxygenTankBoughtHistoryIsUpdated() {
        oxygenProducer.produceOxygen(OxygenGrade.E, 2, results);
        assertEquals(2, results.orderDateHistory.qtyOxygenTankBought);
    }

    @Test
    public void givenExistingOxygenTankBoughtQty_whenOrderOxygenQuantityGradeE_thenOrderDateQtyOxygenTankBoughtHistoryIsUpdated() {
        results.orderDateHistory.qtyOxygenTankBought = 2;
        oxygenProducer.produceOxygen(OxygenGrade.E, 2, results);
        assertEquals(4, results.orderDateHistory.qtyOxygenTankBought);
    }

    @Test
    public void whenOrderOxygenGradeAOnTime_thenNextGradeToProduceIsGradeA() {
        OxygenGrade nextGradeToProduce = oxygenProducer.getNextGradeToProduce(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A);
        assertEquals(OxygenGrade.A, nextGradeToProduce);
    }

    @Test
    public void whenOrderOxygenGradeATooLate_thenNextGradeToProduceIsGradeB() {
        OxygenGrade nextGradeToProduce = oxygenProducer.getNextGradeToProduce(FIFTEEN_DAYS_BEFORE_FESTIVAL, OxygenGrade.A);
        assertEquals(OxygenGrade.B, nextGradeToProduce);
    }

    @Test
    public void whenOrderOxygenGradeBTooLate_thenNextGradeToProduceIsGradeE() {
        OxygenGrade nextGradeToProduce = oxygenProducer.getNextGradeToProduce(FIVE_DAYS_BEFORE_FESTIVAL_DATE, OxygenGrade.B);
        assertEquals(OxygenGrade.E, nextGradeToProduce);
    }

    @Test
    public void whenOrderOxygenGradeAOnLimitDeliveryDate_thenNextGradeToProduceIsGradeE() {
        OxygenGrade nextGradeToProduce = oxygenProducer.getNextGradeToProduce(FESTIVAL_STARTING_DATE, OxygenGrade.E);
        assertEquals(OxygenGrade.E, nextGradeToProduce);
    }

    @Test
    public void whenOrderOxygenGradeEAfterLimitDeliveryDate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> oxygenProducer.getNextGradeToProduce(DURING_FESTIVAL_DATE, OxygenGrade.E));
    }
}