package ca.ulaval.glo4002.booking.persistance.heap;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.Inventory;;

public class HeapOxygenInventory implements OxygenInventory {

    private final EnumMap<OxygenGrade, Integer> inventory;
    private final EnumMap<OxygenGrade, Integer> remaining;

    public HeapOxygenInventory() {
        remaining = initialize();
        inventory = initialize();
    }

    private EnumMap<OxygenGrade, Integer> initialize() {
        EnumMap<OxygenGrade, Integer> collection = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class);
        EnumSet.allOf(OxygenGrade.class)
            .forEach(grade -> collection.put(grade, 0));
            return collection;
    }

    @Override
    public int getInventoryOfGrade(OxygenGrade grade) {
        return inventory.get(grade);
    }

    @Override
    public List<Inventory> getCompleteInventory() {
        return presentInventory();
    }

    private List<Inventory> presentInventory() {
        List<Inventory> inventoryList = new ArrayList<Inventory>();      
        for (OxygenGrade grade : inventory.keySet()) {
            addInventory(inventoryList, grade.name(), inventory.get(grade));
        }
        return inventoryList;
    }  
    
    private void addInventory(List<Inventory> inventory, String grade, int categoryCount) {
        Inventory item = new Inventory();
        item.gradeTankOxygen = grade;
        item.quantity = categoryCount;
        inventory.add(item);
    }

    @Override
    public void setOxygenInventory(OxygenGrade grade, int quantity) {
       inventory.put(grade, quantity);
    }

    @Override
    public void setOxygenRemaining(OxygenGrade grade, int quantity) {
        remaining.put(grade, quantity);
    }

    @Override
    public int getOxygenRemaining(OxygenGrade grade) {
        return remaining.get(grade);
    }
}