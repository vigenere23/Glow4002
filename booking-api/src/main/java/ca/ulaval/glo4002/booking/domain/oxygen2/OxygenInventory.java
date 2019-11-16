package ca.ulaval.glo4002.booking.domain.oxygen2;

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

    public void updateQuantity(OxygenGrade oxygenGrade, int quantity) {
        inventory.put(oxygenGrade, quantity);
    }
}
