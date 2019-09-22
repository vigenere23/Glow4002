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
	Arrays.asList(OxygenGrade.values())
		.forEach(oxygenGrade -> assertEquals(oxygenService.getInventory(oxygenGrade), 0));
    }

    @Test
    public void whenAddSupernovaPass_oxygenGradeEHasQuantityFive() {
	Orderable supernovaPass = new Pass(oneMonthBeforeFestivalDate, PassCategory.SUPERNOVA, oneDate);
	oxygenService.orderOxygen(supernovaPass);
	int inventory = oxygenService.getInventory(OxygenGrade.E);
	assertEquals(inventory, 5);
    }

    @Test
    public void whenAddSupergiantPass_oxygenGradeBHasQuantityThree() {
	Orderable supergiantPass = new Pass(oneMonthBeforeFestivalDate, PassCategory.SUPERGIANT, oneDate);
	oxygenService.orderOxygen(supergiantPass);
	int inventory = oxygenService.getInventory(OxygenGrade.B);
	assertEquals(inventory, 3);
    }

    @Test
    public void whenAddNebulaPass_oxygenGradeAHasQuantityThree() {
	Orderable nebulaPass = new Pass(oneMonthBeforeFestivalDate, PassCategory.NEBULA, oneDate);
	oxygenService.orderOxygen(nebulaPass);
	int inventory = oxygenService.getInventory(OxygenGrade.A);
	assertEquals(inventory, 3);
    }
}
