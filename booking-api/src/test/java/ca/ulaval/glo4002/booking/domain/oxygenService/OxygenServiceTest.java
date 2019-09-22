package ca.ulaval.glo4002.booking.domain.oxygenService;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Orderable;
import ca.ulaval.glo4002.booking.domain.Pass;
import ca.ulaval.glo4002.booking.domain.enumeration.PassCategory;

public class OxygenServiceTest {

    private OxygenService oxygenService;
    final static LocalDate festivalStartingDate = LocalDate.of(2050, 07, 17);
    final static LocalDate oneMonthBeforeFestivalDate = LocalDate.of(2050, 6, 17);
    final static LocalDate fifteenDaysBeforeFestivalDate = LocalDate.of(2050, 7, 2);
    final static LocalDate fiveDaysBeforeFestivalDate = LocalDate.of(2050, 7, 12);
    private List<LocalDate> oneDate;

    @BeforeEach
    public void testInitialize() {
	oxygenService = new OxygenService();
	oneDate = new ArrayList<LocalDate>();
	oneDate.add(LocalDate.of(2050, 7, 18));
    }

    @Test
    public void whenNoPassIsAdded_eachGradeHasQuantityZero() {
	List<Map<String, Object>> inventories = oxygenService.getInventory();
	for (Map<String, Object> inventory : inventories) {
	    assertEquals(inventory.get("quantity"), 0);
	}
    }

    @Test
    public void inventoryHasSizeThree() {
	List<Map<String, Object>> inventories = oxygenService.getInventory();
	assertEquals(inventories.size(), 3);
    }

    @Test
    public void whenAddSupernovaPass_oxygenGradeEHasQuantityFive() {
	Orderable supernovaPass = new Pass(oneMonthBeforeFestivalDate, PassCategory.SUPERNOVA, oneDate);
	oxygenService.orderOxygen(supernovaPass);
	List<Map<String, Object>> inventories = oxygenService.getInventory();
	OxygenNeed oxygenNeed = new OxygenNeed(OxygenGrade.E, 5);
	verifyInventoryQuantityForAnOxygenGrade(inventories, oxygenNeed);
    }

    @Test
    public void whenAddSupergiantPass_oxygenGradeBHasQuantityThree() {
	Orderable supergiantPass = new Pass(oneMonthBeforeFestivalDate, PassCategory.SUPERGIANT, oneDate);
	oxygenService.orderOxygen(supergiantPass);
	List<Map<String, Object>> inventories = oxygenService.getInventory();
	OxygenNeed oxygenNeed = new OxygenNeed(OxygenGrade.B, 3);
	verifyInventoryQuantityForAnOxygenGrade(inventories, oxygenNeed);
    }

    @Test
    public void whenAddNebulaPass_oxygenGradeAHasQuantityThree() {
	Orderable nebulaPass = new Pass(oneMonthBeforeFestivalDate, PassCategory.NEBULA, oneDate);
	oxygenService.orderOxygen(nebulaPass);
	List<Map<String, Object>> inventories = oxygenService.getInventory();
	OxygenNeed oxygenNeed = new OxygenNeed(OxygenGrade.A, 3);
	verifyInventoryQuantityForAnOxygenGrade(inventories, oxygenNeed);
    }

    private void verifyInventoryQuantityForAnOxygenGrade(List<Map<String, Object>> inventories, OxygenNeed oxygenNeed) {
	for (Map<String, Object> inventory : inventories) {
	    if (inventory.get("gradeTankOxygen") == oxygenNeed.getOxygenGrade()) {
		assertEquals(inventory.get("quantity"), oxygenNeed.getOxygenTankQuantity());
	    }
	}
    }
}
