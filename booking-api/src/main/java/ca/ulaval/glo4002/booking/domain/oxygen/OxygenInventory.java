package ca.ulaval.glo4002.booking.domain.oxygen;

public class OxygenInventory {

    OxygenGrade oxygenGrade;
    private int inventory;
    private int remainingQuantity;

    public OxygenInventory(OxygenGrade oxygenGrade, int totalQuantity, int remainingQuantity) {
        this.oxygenGrade = oxygenGrade;
        this.inventory = totalQuantity;
        this.remainingQuantity = remainingQuantity;
    }

    public OxygenGrade getOxygenGrade() {
        return oxygenGrade;
    }

    public void updateInventory(int quantityToFabricate, int requiredQuantity) {
        remainingQuantity = quantityToFabricate - requiredQuantity;
        inventory += quantityToFabricate;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public int getInventory() {
        return inventory;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }
}
