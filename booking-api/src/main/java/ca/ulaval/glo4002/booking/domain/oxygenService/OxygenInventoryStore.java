package ca.ulaval.glo4002.booking.domain.oxygenService;

import java.util.ArrayList;
import java.util.List;

public class OxygenInventoryStore {
    private OxygenTankInventory commercialTankInventory = new OxygenTankInventory(OxygenGrade.E);
    private OxygenTankInventory electrolyzeTankInventory = new OxygenTankInventory(OxygenGrade.B);
    private OxygenTankInventory candleTankInventory = new OxygenTankInventory(OxygenGrade.A);
    private List<OxygenTankInventory> inventories;

    public OxygenInventoryStore() {
	initializeInventories();
    }

    public void adjustInventory(OxygenNeed oxygenNeed) {
	if (oxygenNeed.getOxygenGrade() == OxygenGrade.E) {
	    commercialTankInventory.adjustInventory(oxygenNeed);
	    return;
	} else if (oxygenNeed.getOxygenGrade() == OxygenGrade.B) {
	    electrolyzeTankInventory.adjustInventory(oxygenNeed);
	    return;
	} else {
	    candleTankInventory.adjustInventory(oxygenNeed);
	}
    }

    private void initializeInventories() {
	inventories = new ArrayList<OxygenTankInventory>();
	inventories.add(commercialTankInventory);
	inventories.add(electrolyzeTankInventory);
	inventories.add(candleTankInventory);
    }

    public List<OxygenTankInventory> getInventories() {
	return inventories;
    }
}
