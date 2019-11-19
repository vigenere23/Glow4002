package ca.ulaval.glo4002.booking.domain.oxygen2.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

public class OxygenInventory {

    private Map<OxygenGrade, OxygenInventoryEntry> inventory;

    public OxygenInventory() {
        inventory = new HashMap<>();
        setupInventory();
    }

	private void setupInventory() {
        for (OxygenGrade oxygenGrade : OxygenGrade.values()) {
            save(new OxygenInventoryEntry(oxygenGrade));
        }
    }
    
    public OxygenInventoryEntry find(OxygenGrade oxygenGrade) {
        return inventory.get(oxygenGrade);
    }

    public List<OxygenInventoryEntry> findAll() {
        return new ArrayList<>(inventory.values());
    }

    public void save(OxygenInventoryEntry entry) {
        inventory.put(entry.getOxygenGrade(), entry);
    }
}
