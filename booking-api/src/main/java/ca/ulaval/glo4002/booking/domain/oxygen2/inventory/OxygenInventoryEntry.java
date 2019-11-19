package ca.ulaval.glo4002.booking.domain.oxygen2.inventory;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

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
        assert quantityToAdd >= 1 : "the added quantity must be at least 1";
        surplusQuantity += quantityToAdd;
    }

    public void useQuantity(int quantityToUse) {
        assert quantityToUse >= 1 : "the used quantity must be at least 1";
        assert surplusQuantity - quantityToUse >= 0 : "not enought surplus to use";

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
