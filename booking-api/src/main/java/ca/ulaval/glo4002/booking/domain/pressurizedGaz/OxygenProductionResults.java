package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

public class OxygenProductionResults {

	public HistoryDto orderDateHistory;
	public HistoryDto deliveryDateHistory;
	
	public int quantityTankToAddToInventory;
	public int quantityTankToAddToRemaining;
	public OxygenGrade gradeProduced;
}