package ca.ulaval.glo4002.booking.domain.oxygenService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.booking.domain.Orderable;

public class OxygenService implements OxygenReportable {
    private GasStorable inventoryStore;

    public OxygenService() {
	inventoryStore = new OxygenInventoryStore();
    }

    public void orderOxygen(Orderable order) {
	GasNeedable oxygenNeed = new OxygenNeed();
	oxygenNeed.setNeedsBasedOnPassCategory(order.getPassCategory());
	inventoryStore.adjustInventory(order.getOrderDate(), oxygenNeed);
    }

    public List<Map<String, Object>> getInventory() {
	List<Map<String, Object>> formattedInventories = new ArrayList<Map<String, Object>>();
	List<Inventory> inventories = inventoryStore.getInventories();
	for (Inventory inventory : inventories) {
	    OxygenTankInventory oxygenTankInventory = (OxygenTankInventory) inventory;
	    formattedInventories.add(formatInventory(oxygenTankInventory));
	}
	return formattedInventories;
    }

    private Map<String, Object> formatInventory(OxygenTankInventory inventory) {
	Map<String, Object> formattedInventory = new HashMap<String, Object>();
	formattedInventory.put("quantity", inventory.getInventory());
	formattedInventory.put("gradeTankOxygen", inventory.getOxygenCategory());
	return formattedInventory;
    }
}
