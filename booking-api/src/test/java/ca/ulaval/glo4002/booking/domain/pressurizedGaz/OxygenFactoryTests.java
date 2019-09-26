package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OxygenFactoryTests {

    private OxygenFactory oxygenFactory;
    final static LocalDate festivalStartingDate = LocalDate.of(2050, 7, 17);
    final static LocalDate oneMonthBeforeFestivalDate = LocalDate.of(2050, 6, 17);
    final static LocalDate fifteenDaysBeforeFestivalDate = LocalDate.of(2050, 7, 2);
    final static LocalDate fiveDaysBeforeFestivalDate = LocalDate.of(2050, 7, 12);

    @BeforeEach
    public void testInitialize() {
	oxygenFactory = new OxygenFactory(festivalStartingDate);
    }

    @Test
    public void given_orderOxygen_when_defaultTemplate_then_fabricationQuantityIsAdded() {
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(5, OxygenGrade.A);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygen_then_multipleOfFabricationQuantityIsAdded() {
	EnumMap<OxygenGrade, Integer> expectedQuantity = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
	expectedQuantity.put(OxygenGrade.A, 6);
	oxygenFactory.setTemplatedOxygenOrder(expectedQuantity);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(10, OxygenGrade.A);
    }

    @Test
    public void given_setMultiTemplate_when_orderOxygen_then_correspondingMultipleOfFabricationQuantityIsAdded() {
	EnumMap<OxygenGrade, Integer> expectedQuantity = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
	expectedQuantity.put(OxygenGrade.A, 4);
	expectedQuantity.put(OxygenGrade.B, 2);
	expectedQuantity.put(OxygenGrade.E, 3);
	oxygenFactory.setTemplatedOxygenOrder(expectedQuantity);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.B);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.E);
	assertInventoryOfGrade(5, OxygenGrade.A);
	assertInventoryOfGrade(3, OxygenGrade.B);
	assertInventoryOfGrade(3, OxygenGrade.E);
    }

    @Test
    public void given_orderOxygenTwice_when_defaultTemplate_then_fabricationQuantityIsAdded() {
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(5, OxygenGrade.A);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygenTwice_then_multipleOfFabricationQuantityIsAdded() {
	EnumMap<OxygenGrade, Integer> expectedQuantity = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
	expectedQuantity.put(OxygenGrade.A, 4);
	oxygenFactory.setTemplatedOxygenOrder(expectedQuantity);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	oxygenFactory.orderTemplatedOxygenQuantity(oneMonthBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(10, OxygenGrade.A);
    }

    @Test
    public void given_orderOxygenGradeATooLate_when_defaultTemplate_then_noGradeAOxygenIsAdded() {
	oxygenFactory.orderTemplatedOxygenQuantity(fifteenDaysBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(0, OxygenGrade.A);
    }

    @Test
    public void given_orderOxygenGradeATooLateForGradeA_when_defaultTemplate_then_GradeBOxygenIsAdded() {
	oxygenFactory.orderTemplatedOxygenQuantity(fifteenDaysBeforeFestivalDate, OxygenGrade.A);
	assertInventoryOfGrade(3, OxygenGrade.B);
    }

    @Test
    public void given_orderOxygenGradeATooLateForGradesAandB_when_defaultTemplate_then_GradeEOxygenIsAdded() {
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
