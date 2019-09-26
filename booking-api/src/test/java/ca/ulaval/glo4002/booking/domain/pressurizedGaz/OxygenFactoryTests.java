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
	oxygenFactory.orderTemplatedOxygenQuantity(OxygenGrade.A);
	assertInventoryOfGrade(5, OxygenGrade.A);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygen_then_multipleOfFabricationQuantityIsAdded() {
	EnumMap<OxygenGrade, Integer> expectedQuantity = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
	expectedQuantity.put(OxygenGrade.A, 6);
	oxygenFactory.setTemplatedOxygenOrder(expectedQuantity);
	oxygenFactory.orderTemplatedOxygenQuantity(OxygenGrade.A);
	assertInventoryOfGrade(10, OxygenGrade.A);
    }

    @Test
    public void given_setMultiTemplate_when_orderOxygen_then_correspondingMultipleOfFabricationQuantityIsAdded() {
	EnumMap<OxygenGrade, Integer> expectedQuantity = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
	expectedQuantity.put(OxygenGrade.A, 4);
	expectedQuantity.put(OxygenGrade.B, 2);
	expectedQuantity.put(OxygenGrade.E, 3);
	oxygenFactory.setTemplatedOxygenOrder(expectedQuantity);
	oxygenFactory.orderTemplatedOxygenQuantity(OxygenGrade.A);
	oxygenFactory.orderTemplatedOxygenQuantity(OxygenGrade.B);
	oxygenFactory.orderTemplatedOxygenQuantity(OxygenGrade.E);
	assertInventoryOfGrade(5, OxygenGrade.A);
	assertInventoryOfGrade(3, OxygenGrade.B);
	assertInventoryOfGrade(3, OxygenGrade.E);
    }

    @Test
    public void given_orderOxygenTwice_when_defaultTemplate_then_fabricationQuantityIsAdded() {
	oxygenFactory.orderTemplatedOxygenQuantity(OxygenGrade.A);
	oxygenFactory.orderTemplatedOxygenQuantity(OxygenGrade.A);
	assertInventoryOfGrade(5, OxygenGrade.A);
    }

    @Test
    public void given_setOneTemplate_when_orderOxygenTwice_then_multipleOfFabricationQuantityIsAdded() {
	EnumMap<OxygenGrade, Integer> expectedQuantity = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
	expectedQuantity.put(OxygenGrade.A, 4);
	oxygenFactory.setTemplatedOxygenOrder(expectedQuantity);
	oxygenFactory.orderTemplatedOxygenQuantity(OxygenGrade.A);
	oxygenFactory.orderTemplatedOxygenQuantity(OxygenGrade.A);
	assertInventoryOfGrade(10, OxygenGrade.A);
    }

    private OxygenReportable getReportable() {
	return oxygenFactory;
    }

    private void assertInventoryOfGrade(int inventoryCount, OxygenGrade grade) {
	assertTrue(inventoryCount == getReportable().getInventory(grade));
    }
}
