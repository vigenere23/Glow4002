package ca.ulaval.glo4002.booking.persistance.heap;

import java.util.EnumMap;
import java.util.EnumSet;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenGrade;

public class InMemoryOxygenInventory implements OxygenInventory {

    private EnumMap<OxygenGrade, Integer> inventory;

    public InMemoryOxygenInventory() {
        inventory = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
        EnumSet.allOf(OxygenGrade.class)
            .forEach(grade -> inventory.put(grade, 0));
    }

    @Override
    public void addOxygenToInventory(OxygenGrade grade, int quantity) {
        inventory.put(grade, (inventory.get(grade) + quantity));
    }

    @Override
    public int getInventoryOfGrade(OxygenGrade grade) {
        return inventory.get(grade);
    }
}