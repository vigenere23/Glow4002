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
	 public void given_orderOxygen_when_defaultTemplate_then_oneItenIsAdd() {
		 orderGradeAndAssertInventory(1, OxygenGrade.A);
	 }
	 
	 @Test
	 public void when_orderOxugenTwice_then_correspopndingQuantityIsAdd() {
		 oxygenFactory.orderTemplatedOxygenQuantity(OxygenGrade.A);
		 oxygenFactory.orderTemplatedOxygenQuantity(OxygenGrade.A);
		 assertTrue(2 == getReportable().getInventory(OxygenGrade.A));
	 }
	 
	 @Test
	 public void given_setOneTemplate_when_orderOxugen_then_correspopndingQuantityIsAdd() {
		 EnumMap<OxygenGrade, Integer> expectedQuantity = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
		 expectedQuantity.put(OxygenGrade.A, 10);
		 oxygenFactory.setTemplatedOxygenOrder(expectedQuantity);
		 orderGradeAndAssertInventory(10, OxygenGrade.A);
	 }
	 
	 
	 @Test
	 public void given_setMultiTemplate_when_orderOxugen_then_correspopndingQuantityIsAdd() {
		 EnumMap<OxygenGrade, Integer> expectedQuantity = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
		 expectedQuantity.put(OxygenGrade.A, 10);
		 expectedQuantity.put(OxygenGrade.B, 2);
		 expectedQuantity.put(OxygenGrade.E, 5);
		 oxygenFactory.setTemplatedOxygenOrder(expectedQuantity);
		 orderGradeAndAssertInventory(10, OxygenGrade.A);
		 orderGradeAndAssertInventory(2, OxygenGrade.B);
		 orderGradeAndAssertInventory(5, OxygenGrade.E);
	 }
	 
	 private OxygenReportable getReportable() {
		 return oxygenFactory;
	 }
	 
	 private void orderGradeAndAssertInventory(int inventoryCount, OxygenGrade grade) {
		 oxygenFactory.orderTemplatedOxygenQuantity(grade);
		 assertTrue(inventoryCount == getReportable().getInventory(grade));
	 }
}
