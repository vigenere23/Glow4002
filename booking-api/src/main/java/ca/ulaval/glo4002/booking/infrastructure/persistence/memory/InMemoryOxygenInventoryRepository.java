package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;

public class InMemoryOxygenInventoryRepository implements OxygenInventoryRepository {

    private Map<OxygenGrade, OxygenInventoryEntry> inventory;

    public InMemoryOxygenInventoryRepository() {
        inventory = new HashMap<>();
        setupInventory();
    }

	private void setupInventory() {
        for (OxygenGrade oxygenGrade : OxygenGrade.values()) {
            save(new OxygenInventoryEntry(oxygenGrade));
        }
    }
    
    @Override
    public OxygenInventoryEntry find(OxygenGrade oxygenGrade) {
        return inventory.get(oxygenGrade);
    }

    @Override
    public List<OxygenInventoryEntry> findAll() {
        return new ArrayList<>(inventory.values());
    }

    @Override
    public void save(OxygenInventoryEntry entry) {
        inventory.put(entry.getOxygenGrade(), entry);
    }
}
