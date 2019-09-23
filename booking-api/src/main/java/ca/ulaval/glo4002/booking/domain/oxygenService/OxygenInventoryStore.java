package ca.ulaval.glo4002.booking.domain.oxygenService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OxygenInventoryStore {
    private List<OxygenTankInventory> inventories;

    public OxygenInventoryStore() {
	initializeInventories();
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

    public List<OxygenTankInventory> getInventories() {
	return inventories;
    }
}
