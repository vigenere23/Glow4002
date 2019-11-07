package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import java.util.EnumMap;
import java.util.EnumSet;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;

public class HeapOxygenInventoryRepository implements OxygenInventoryRepository {

    private EnumMap<OxygenGrade, OxygenInventory> inventories;

    public HeapOxygenInventoryRepository() {
        inventories = initializeInventories();
    }

    private EnumMap<OxygenGrade, OxygenInventory> initializeInventories() {
        EnumMap<OxygenGrade, OxygenInventory> collection = new EnumMap<>(OxygenGrade.class);
        EnumSet.allOf(OxygenGrade.class)
            .forEach(grade -> collection.put(grade, new OxygenInventory(grade, 0, 0)));
            return collection;
    }

    @Override
    public void saveOxygenInventories(EnumMap<OxygenGrade, OxygenInventory> inventories) {
        this.inventories = inventories;
    }

    @Override
    public EnumMap<OxygenGrade, OxygenInventory> findInventories() {
        return inventories;
    }
}