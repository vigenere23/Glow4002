package ca.ulaval.glo4002.booking.domain.oxygen.inventory;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenInventoryEntry {

    private OxygenGrade oxygenGrade;
    private int usedQuantity;
    private int surplusQuantity;

    public OxygenInventoryEntry(OxygenGrade oxygenGrade) {
        this.oxygenGrade = oxygenGrade;
        usedQuantity = 0;
        surplusQuantity = 0;
    }

    public void addQuantity(int quantityToAdd) {
        if (quantityToAdd < 0) {
            throw new IllegalArgumentException("the added quantity must be positive");
        }

        surplusQuantity += quantityToAdd;
    }

    public void useQuantity(int quantityToUse) {
        if (quantityToUse < 0) {
            throw new IllegalArgumentException("the used quantity must be positive");
        }
        if (surplusQuantity - quantityToUse < 0) {
            throw new IllegalArgumentException("not enought surplus to use");
        }

        usedQuantity += quantityToUse;
        surplusQuantity -= quantityToUse;
    }

    public OxygenGrade getOxygenGrade() {
        return oxygenGrade;
    }

    public int getSurplusQuantity() {
		return surplusQuantity;
    }

    public int getTotalQuantity() {
        return usedQuantity + surplusQuantity;
    }
}
