package ca.ulaval.glo4002.booking.domain.oxygen2.inventory;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

public class OxygenInventory {

    private Map<OxygenGrade, Integer> inventory;

    public OxygenInventory() {
        inventory = new HashMap<>();
        setupInventory();
    }

	private void setupInventory() {
        for (OxygenGrade oxygenGrade : OxygenGrade.values()) {
            inventory.put(oxygenGrade, 0);
        }
    }

    public int getQuantity(OxygenGrade oxygenGrade) {
		return inventory.get(oxygenGrade);
	}

    public void addQuantity(OxygenGrade oxygenGrade, int quantity) {
        int newQuantity = getQuantity(oxygenGrade) + quantity;
        updateQuantity(oxygenGrade, newQuantity);
    }

    public void removeQuantity(OxygenGrade oxygenGrade, int quantity) {
        int newQuantity = getQuantity(oxygenGrade) - quantity;
        updateQuantity(oxygenGrade, newQuantity);
    }

    private void updateQuantity(OxygenGrade oxygenGrade, int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("the inventory cannot have negative quantities");
        
        inventory.put(oxygenGrade, quantity);
    }
}
