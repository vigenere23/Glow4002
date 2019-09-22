package ca.ulaval.glo4002.booking.domain.oxygenService;

import java.time.LocalDate;

public class OxygenBottleInventory implements Inventory {
    private int totalQuantity = 0;
    private OxygenGrade oxygenCategory;

    public OxygenBottleInventory(OxygenGrade oxygenCategory) {
	this.oxygenCategory = oxygenCategory;
    }

    public void adjustInventory(LocalDate orderDate, GasNeedable gasNeed) {
	OxygenNeed oxygenNeed = (OxygenNeed) gasNeed;
	totalQuantity += oxygenNeed.getOxygenBottleQuantity();
    }

    public int getInventory() {
	return totalQuantity;
    }

    public OxygenGrade getOxygenCategory() {
	return oxygenCategory;
    }

}
