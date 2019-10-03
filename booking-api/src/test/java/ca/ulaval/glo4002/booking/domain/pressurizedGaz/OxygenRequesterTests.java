package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenPersistance;
import ca.ulaval.glo4002.booking.persistance.heap.HeapOxygenPersistance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OxygenRequesterTests {

    private OxygenRequester oxygenRequester;
    private final static OffsetDateTime festivalStartingDate = OffsetDateTime.of(2050, 7, 17, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime oneMonthBeforeFestivalDate = OffsetDateTime.of(2050, 6, 17, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime fifteenDaysBeforeFestivalDate = OffsetDateTime.of(2050, 7, 2, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime fiveDaysBeforeFestivalDate = OffsetDateTime.of(2050, 7, 12, 0, 0, 0, 0, ZoneOffset.UTC);
    private final static OffsetDateTime duringFestival = OffsetDateTime.of(2050, 7, 18, 0, 0, 0, 0, ZoneOffset.UTC);

    @BeforeEach
    public void testInitialize() {
        OxygenPersistance oxygenPersistance = new HeapOxygenPersistance();
        oxygenRequester = new OxygenRequester(festivalStartingDate, oxygenPersistance);
    }

    @Test
    public void given_orderOxygen_when_defaultTemplate_then_fabricationQuantityIsAdded() {
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 3);
        assertInventoryOfGrade(5, OxygenGrade.A);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygen_then_multipleOfFabricationQuantityIsAdded() {
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 6);
        assertInventoryOfGrade(10, OxygenGrade.A);
    }

    @Test
    public void given_setMultiTemplate_when_orderOxygen_then_correspondingMultipleOfFabricationQuantityIsAdded() {
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 4);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.B, 2);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.E, 3);
        assertInventoryOfGrade(5, OxygenGrade.A);
        assertInventoryOfGrade(3, OxygenGrade.B);
        assertInventoryOfGrade(3, OxygenGrade.E);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygenTwice_then_multipleOfFabricationQuantityIsAdded() {
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 4);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 4);
        assertInventoryOfGrade(10, OxygenGrade.A);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygenThreeTimes_then_threeFabricationQuantityIsAdded() {
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 4);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 4);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 4);
        assertInventoryOfGrade(15, OxygenGrade.A);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygenThreeTimes_then_twoFabricationQuantityIsAdded() {
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 3);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 3);
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 3);
        assertInventoryOfGrade(10, OxygenGrade.A);
    }

    @Test
    public void when_orderOxygenGradeATooLate_then_noGradeAOxygenIsAdded() {
        oxygenRequester.orderOxygen(fifteenDaysBeforeFestivalDate, OxygenGrade.A, 1);
        assertInventoryOfGrade(0, OxygenGrade.A);
    }

    @Test
    public void when_orderOxygenGradeATooLateForGradeA_then_GradeBOxygenIsAdded() {
        oxygenRequester.orderOxygen(fifteenDaysBeforeFestivalDate, OxygenGrade.A, 1);
        assertInventoryOfGrade(3, OxygenGrade.B);
    }

    @Test
    public void when_orderOxygenGradeATooLateForGradesAandB_then_GradeEOxygenIsAdded() {
        oxygenRequester.orderOxygen(fiveDaysBeforeFestivalDate, OxygenGrade.A, 1);
        assertInventoryOfGrade(1, OxygenGrade.E);
    }

    @Test
    public void when_orderOxygenGradeAOnLimitDeliveryDate_then_GradeEOxygenIsAdded() {
        oxygenRequester.orderOxygen(festivalStartingDate, OxygenGrade.A, 1);
        assertInventoryOfGrade(1, OxygenGrade.E);
    }

    @Test
    public void when_orderOxygenGradeATooLateForGradeA_then_storedGradeAIsUsed() {
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 1);
        oxygenRequester.orderOxygen(fifteenDaysBeforeFestivalDate, OxygenGrade.A, 1);
        assertInventoryOfGrade(0, OxygenGrade.B);
    }

    @Test
    public void when_orderOxygenGradeATooLateForNewGradeA_then_storedGradeAAndGradeBAreUsed() {
        oxygenRequester.orderOxygen(oneMonthBeforeFestivalDate, OxygenGrade.A, 4);
        oxygenRequester.orderOxygen(fifteenDaysBeforeFestivalDate, OxygenGrade.A, 4);
        assertInventoryOfGrade(3, OxygenGrade.B);
    }

    @Test
    public void when_orderOxygenGradeAAfterLimitDeliveryDate_then_exception() {
        assertThrows(IllegalArgumentException.class, () -> oxygenRequester.orderOxygen(duringFestival, OxygenGrade.A, 1));
    }

    private void assertInventoryOfGrade(int expectedQuantity, OxygenGrade grade) {
        List<Inventory> inventories = oxygenRequester.getInventory();
        for (Inventory inventory: inventories) {
            if (inventory.gradeTankOxygen.equals(grade.toString())) {
                int currentQuantity = inventory.quantity;
                assertEquals(expectedQuantity, currentQuantity);
            }
        }
    }
}