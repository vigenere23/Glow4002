package ca.ulaval.glo4002.booking.domain.oxygen;

public class OxygenInventory {
    private int totalQuantity;
    private int remainingQuantity;

    public OxygenInventory(int totalQuantity, int remainingQuantity) {
        this.totalQuantity = totalQuantity;
        this.remainingQuantity = remainingQuantity;
    }

    public void updateInventory(int quantityToFabricate, int quantityOfTanksLacking) {
        remainingQuantity = quantityToFabricate - quantityOfTanksLacking;
        totalQuantity += quantityToFabricate;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }
}
