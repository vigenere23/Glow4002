package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OxygenFactoryTests {

    private OxygenFactory oxygenFactory;

    @BeforeEach
    public void testInitialize() {
	oxygenFactory = new OxygenFactory();
    }

    @Test
    public void given_orderOxygen_when_defaultTemplate_then_fabricationQuantityIsAdded() {
	orderGradeAndAssertInventory(OxygenGrade.A, 5);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygen_then_multipleOfFabricationQuantityIsAdded() {
	EnumMap<OxygenGrade, Integer> expectedQuantity = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
	expectedQuantity.put(OxygenGrade.A, 6);
	oxygenFactory.setTemplatedOxygenOrder(expectedQuantity);
	orderGradeAndAssertInventory(OxygenGrade.A, 10);
    }

    @Test
    public void given_setMultiTemplate_when_orderOxygen_then_correspondingMultipleOfFabricationQuantityIsAdded() {
	EnumMap<OxygenGrade, Integer> expectedQuantity = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
	expectedQuantity.put(OxygenGrade.A, 4);
	expectedQuantity.put(OxygenGrade.B, 2);
	expectedQuantity.put(OxygenGrade.E, 3);
	oxygenFactory.setTemplatedOxygenOrder(expectedQuantity);
	orderGradeAndAssertInventory(OxygenGrade.A, 5);
	orderGradeAndAssertInventory(OxygenGrade.B, 3);
	orderGradeAndAssertInventory(OxygenGrade.E, 3);
    }

    private OxygenReportable getReportable() {
	return oxygenFactory;
    }

    private void orderGradeAndAssertInventory(OxygenGrade grade, int inventoryCount) {
	oxygenFactory.orderTemplatedOxygenQuantity(grade);
	assertTrue(inventoryCount == getReportable().getInventory(grade));
    }
}
