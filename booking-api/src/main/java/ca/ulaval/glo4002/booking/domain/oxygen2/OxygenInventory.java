package ca.ulaval.glo4002.booking.domain.oxygen2;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenInventory {

    private Map<OxygenGrade, Integer> inventory;

    public OxygenInventory() {
        inventory = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        for (OxygenGrade oxygenGrade : OxygenGrade.values()) {
            inventory.put(oxygenGrade, 0);
        }
    }

    public int getRemainingQuantityOfGrade(OxygenGrade oxygenGrade) {
        return inventory.get(oxygenGrade);
    }

    public void reduceQuantity(OxygenGrade oxygenGrade, int quantityToReduce) {
        int oldQuantity = getRemainingQuantityOfGrade(oxygenGrade);
        int newQuantity = oldQuantity - quantityToReduce;
        inventory.put(oxygenGrade, newQuantity);
    }
}
