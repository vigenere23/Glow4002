package ca.ulaval.glo4002.booking.domain.oxygenService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OxygenInventoryStore implements GasStorable {
    private Inventory commercialBottleInventory = new OxygenBottleInventory(OxygenGrade.E);
    private Inventory electrolyzeBottleInventory = new OxygenBottleInventory(OxygenGrade.B);
    private Inventory candleBottleInventory = new OxygenBottleInventory(OxygenGrade.A);
    private List<Inventory> inventories;

    public OxygenInventoryStore() {
	initializeInventories();
    }

    public void adjustInventory(LocalDate orderDate, GasNeedable gasNeed) {
	OxygenNeed oxygenNeed = (OxygenNeed) gasNeed;
	if (oxygenNeed.getOxygenGrade() == OxygenGrade.E) {
	    commercialBottleInventory.adjustInventory(orderDate, gasNeed);
	    return;
	} else if (oxygenNeed.getOxygenGrade() == OxygenGrade.B) {
	    electrolyzeBottleInventory.adjustInventory(orderDate, gasNeed);
	    return;
	} else {
	    candleBottleInventory.adjustInventory(orderDate, gasNeed);
	}
    }

    public void initializeInventories() {
	inventories = new ArrayList<Inventory>();
	inventories.add(commercialBottleInventory);
	inventories.add(electrolyzeBottleInventory);
	inventories.add(candleBottleInventory);
    }

    public List<Inventory> getInventories() {
	return inventories;
    }

}
