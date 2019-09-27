package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OxygenFactoryTests {

    private OxygenRequester oxygenFactory;
    final static LocalDate festivalStartingDate = LocalDate.of(2050, 7, 17);
    final static LocalDate oneMonthBeforeFestivalDate = LocalDate.of(2050, 6, 17);
    final static LocalDate fifteenDaysBeforeFestivalDate = LocalDate.of(2050, 7, 2);
    final static LocalDate fiveDaysBeforeFestivalDate = LocalDate.of(2050, 7, 12);

    @BeforeEach
    public void testInitialize() {
	oxygenFactory = new OxygenRequester(festivalStartingDate);
    }

    @Test
    public void given_orderOxygen_when_defaultTemplate_then_fabricationQuantityIsAdded() {
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(5, OxygenGrade.A);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygen_then_multipleOfFabricationQuantityIsAdded() {
	oxygenFactory.setTemplatedOxygenOrder(OxygenGrade.A, 6);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(10, OxygenGrade.A);
    }

    @Test
    public void given_setMultiTemplate_when_orderOxygen_then_correspondingMultipleOfFabricationQuantityIsAdded() {
	oxygenFactory.setTemplatedOxygenOrder(OxygenGrade.A, 4);
	oxygenFactory.setTemplatedOxygenOrder(OxygenGrade.B, 2);
	oxygenFactory.setTemplatedOxygenOrder(OxygenGrade.E, 3);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.B);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.E);
	assertInventoryOfGrade(5, OxygenGrade.A);
	assertInventoryOfGrade(3, OxygenGrade.B);
	assertInventoryOfGrade(3, OxygenGrade.E);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygenTwice_then_multipleOfFabricationQuantityIsAdded() {
	oxygenFactory.setTemplatedOxygenOrder(OxygenGrade.A, 4);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(10, OxygenGrade.A);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygenThreeTimes_then_threeFabricationQuantityIsAdded() {
	oxygenFactory.setTemplatedOxygenOrder(OxygenGrade.A, 4);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(15, OxygenGrade.A);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygenThreeTimes_then_twoFabricationQuantityIsAdded() {
	oxygenFactory.setTemplatedOxygenOrder(OxygenGrade.A, 3);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(10, OxygenGrade.A);
    }

    @Test
    public void when_orderOxygenGradeATooLate_then_noGradeAOxygenIsAdded() {
	oxygenFactory.orderTemplatedOxygenQuantity(fifteenDaysBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(0, OxygenGrade.A);
    }

    @Test
    public void when_orderOxygenGradeATooLateForGradeA_then_GradeBOxygenIsAdded() {
	oxygenFactory.orderTemplatedOxygenQuantity(fifteenDaysBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(3, OxygenGrade.B);
    }

    @Test
    public void when_orderOxygenGradeATooLateForGradesAandB_then_GradeEOxygenIsAdded() {
	oxygenFactory.orderTemplatedOxygenQuantity(fiveDaysBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(1, OxygenGrade.E);
    }

    private OxygenReportable getReportable() {
	return oxygenFactory;
    }

    private void assertInventoryOfGrade(int inventoryCount, OxygenGrade grade) {
	assertTrue(inventoryCount == getReportable().getInventory(grade));
    }
}
