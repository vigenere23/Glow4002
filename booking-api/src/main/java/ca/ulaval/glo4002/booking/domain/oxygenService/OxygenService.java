package ca.ulaval.glo4002.booking.domain.oxygenService;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.Orderable;

public class OxygenService implements OxygenReportable {
    private OxygenInventoryStore inventoryStore;

    public OxygenService() {
	inventoryStore = new OxygenInventoryStore();
    }

    public void orderOxygen(Orderable order) {
	OxygenNeedFactory oxygenNeedFactory = new OxygenNeedFactory();
	OxygenNeed oxygenNeed = oxygenNeedFactory.getOxygenNeed(order.getPassCategory());
	inventoryStore.adjustInventory(oxygenNeed);
    }

    public int getInventory(OxygenGrade oxygenGrade) {
	List<OxygenTankInventory> inventories = inventoryStore.getInventories();
	for (OxygenTankInventory inventory : inventories) {
	    if (inventory.getOxygenGrade() == oxygenGrade) {
		return inventory.getInventory();
	    }
	}
	throw new IllegalArgumentException(String.format("No oxygen inventory for oxygen grade %s.", oxygenGrade));
    }
}
