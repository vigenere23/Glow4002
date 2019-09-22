package ca.ulaval.glo4002.booking.domain.oxygenService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OxygenInventoryStore implements GasStorable {
    private Inventory commercialTankInventory = new OxygenTankInventory(OxygenGrade.E);
    private Inventory electrolyzeTankInventory = new OxygenTankInventory(OxygenGrade.B);
    private Inventory candleTankInventory = new OxygenTankInventory(OxygenGrade.A);
    private List<Inventory> inventories;

    public OxygenInventoryStore() {
	initializeInventories();
    }

    public void adjustInventory(LocalDate orderDate, GasNeedable gasNeed) {
	OxygenNeed oxygenNeed = (OxygenNeed) gasNeed;
	if (oxygenNeed.getOxygenGrade() == OxygenGrade.E) {
	    commercialTankInventory.adjustInventory(orderDate, gasNeed);
	    return;
	} else if (oxygenNeed.getOxygenGrade() == OxygenGrade.B) {
	    electrolyzeTankInventory.adjustInventory(orderDate, gasNeed);
	    return;
	} else {
	    candleTankInventory.adjustInventory(orderDate, gasNeed);
	}
    }

    public void initializeInventories() {
	inventories = new ArrayList<Inventory>();
	inventories.add(commercialTankInventory);
	inventories.add(electrolyzeTankInventory);
	inventories.add(candleTankInventory);
    }

    public List<Inventory> getInventories() {
	return inventories;
    }
}
