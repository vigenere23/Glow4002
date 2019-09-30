package ca.ulaval.glo4002.booking.interfaces.rest.response.Oxygen;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;


public class ReportOxygenResponse {
	 public final List<Inventory> oxygenInventory;
	 public final List<History> oxygenHistory;
	 
	 private Glow4002 festival;
	 
	@JsonCreator
    public ReportOxygenResponse(Glow4002 festival) {
		this.festival = festival;
		this.oxygenInventory = presentInventory();
		this.oxygenHistory = createHistory();
    }
    
    private List<Inventory> presentInventory() {
    	List<Inventory> inventoryList = new ArrayList<Inventory>();
    	addInventory(inventoryList, "A", festival.getGradeAOxygenInventory());
    	addInventory(inventoryList, "B", festival.getGradeBOxygenInventory());
    	addInventory(inventoryList, "E", festival.getGradeEOxygenInventory());
    	return inventoryList;
    }
    
    private List<History> createHistory() {
		List<History> history =new ArrayList<History>();
			
    	return history;
    }
    
    private void addInventory(List<Inventory> inventory, String grade, int categoryCount) {
    	Inventory item = new Inventory();
    	item.gradeTankOxygen = grade;
    	item.quantity = categoryCount;
    	inventory.add(item);
    }
}
