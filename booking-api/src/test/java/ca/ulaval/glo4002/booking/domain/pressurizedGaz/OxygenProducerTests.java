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
        results.gradeProduced = OxygenGrade.A;
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
    public void whenOrderOxygenQuantityMoreThanMinimumFabricationQuantity_thenMultipleOfMinimumFabricationQuantityIsAdded() {
        oxygenProducer.produceOxygen(OxygenGrade.A, 6, results);
        assertEquals(10, results.quantityTankToAddToInventory);
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
    public void when_orderOxygenGradeAOnLimitDeliveryDate_thenRealGradeToProduceIsGradeE() {
        OxygenGrade realGradeToProduce = oxygenProducer.getRealGradeToProduce(festivalStartingDate, OxygenGrade.A);
        assertEquals(OxygenGrade.E, realGradeToProduce);
    }

    @Test
    public void when_orderOxygenGradeAAfterLimitDeliveryDate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> oxygenProducer.getRealGradeToProduce(duringFestival, OxygenGrade.A));
    }
}