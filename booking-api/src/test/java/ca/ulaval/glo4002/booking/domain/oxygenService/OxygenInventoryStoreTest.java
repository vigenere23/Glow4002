package ca.ulaval.glo4002.booking.domain.oxygenService;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Orderable;
import ca.ulaval.glo4002.booking.domain.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.PassCategory;

public class OxygenInventoryStoreTest {

    private OxygenInventoryStore oxygenInventoryStore;
    final static LocalDate festivalStartingDate = LocalDate.of(2050, 07, 17);
    final static LocalDate oneMonthBeforeFestivalDate = LocalDate.of(2050, 6, 17);
    final static LocalDate fifteenDaysBeforeFestivalDate = LocalDate.of(2050, 7, 2);
    final static LocalDate fiveDaysBeforeFestivalDate = LocalDate.of(2050, 7, 12);
    private List<LocalDate> oneDate;

    @BeforeEach
    public void testInitialize() {
	oxygenInventoryStore = new OxygenInventoryStore();
	oneDate = new ArrayList<LocalDate>();
	oneDate.add(LocalDate.of(2050, 7, 18));
    }

    @Test
    public void whenNoPassIsAdded_eachGradeHasQuantityZero() {
	Arrays.asList(OxygenGrade.values())
		.forEach(oxygenGrade -> assertEquals(oxygenInventoryStore.getInventory(oxygenGrade), 0));
    }

    @Test
    public void whenAddSupernovaPass_oxygenGradeEHasQuantityFive() {
	Orderable supernovaPass = new Pass(oneMonthBeforeFestivalDate, PassCategory.SUPERNOVA, oneDate);
	oxygenInventoryStore.orderOxygen(supernovaPass);
	int inventory = oxygenInventoryStore.getInventory(OxygenGrade.E);
	assertEquals(inventory, 5);
    }

    @Test
    public void whenAddSupergiantPass_oxygenGradeBHasQuantityThree() {
	Orderable supergiantPass = new Pass(oneMonthBeforeFestivalDate, PassCategory.SUPERGIANT, oneDate);
	oxygenInventoryStore.orderOxygen(supergiantPass);
	int inventory = oxygenInventoryStore.getInventory(OxygenGrade.B);
	assertEquals(inventory, 3);
    }

    @Test
    public void whenAddNebulaPass_oxygenGradeAHasQuantityThree() {
	Orderable nebulaPass = new Pass(oneMonthBeforeFestivalDate, PassCategory.NEBULA, oneDate);
	oxygenInventoryStore.orderOxygen(nebulaPass);
	int inventory = oxygenInventoryStore.getInventory(OxygenGrade.A);
	assertEquals(inventory, 3);
    }
}
