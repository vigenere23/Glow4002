package ca.ulaval.glo4002.booking.domain.oxygenService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.Orderable;

public class OxygenInventoryStore {
    private List<OxygenTankInventory> inventories;

    public OxygenInventoryStore() {
	initializeInventories();
    }

    public void orderOxygen(Orderable order) {
	OxygenNeedFactory oxygenNeedFactory = new OxygenNeedFactory();
	OxygenNeed oxygenNeed = oxygenNeedFactory.createOxygenNeed(order.getPassCategory());
	adjustInventory(oxygenNeed);
    }

    public void adjustInventory(OxygenNeed oxygenNeed) {
	for (OxygenTankInventory inventory : inventories) {
	    if (inventory.getOxygenGrade() == oxygenNeed.getOxygenGrade()) {
		inventory.adjustInventory(oxygenNeed);
	    }
	}
    }

    private void initializeInventories() {
	inventories = new ArrayList<OxygenTankInventory>();
	Arrays.asList(OxygenGrade.values())
		.forEach(oxygenGrade -> inventories.add(new OxygenTankInventory(oxygenGrade)));
    }

    public int getInventory(OxygenGrade oxygenGrade) {
	for (OxygenTankInventory inventory : inventories) {
	    if (inventory.getOxygenGrade() == oxygenGrade) {
		return inventory.getInventory();
	    }
	}
	throw new IllegalArgumentException(String.format("No oxygen inventory for oxygen grade %s.", oxygenGrade));
    }
}
