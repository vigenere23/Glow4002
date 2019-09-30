package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenGrade;

public interface OxygenInventory {
	
	public void addOxygenToInventory(OxygenGrade grade, int quantity);
	public int getInventoryOfGrade(OxygenGrade grade);
}
