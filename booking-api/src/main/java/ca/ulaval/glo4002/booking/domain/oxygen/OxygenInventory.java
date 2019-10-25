package ca.ulaval.glo4002.booking.domain.oxygen;

public class OxygenInventory {
    OxygenGrade oxygenGrade;
    private int totalQuantity;
    private int remainingQuantity;

    public OxygenInventory(OxygenGrade oxygenGrade, int totalQuantity, int remainingQuantity) {
        this.oxygenGrade = oxygenGrade;
        this.totalQuantity = totalQuantity;
        this.remainingQuantity = remainingQuantity;
    }

    public OxygenGrade getOxygenGrade() {
        return oxygenGrade;
    }

    public void updateInventory(int quantityToFabricate, int quantityOfTanksLacking) {
        remainingQuantity = quantityToFabricate - quantityOfTanksLacking;
        totalQuantity += quantityToFabricate;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }
}
