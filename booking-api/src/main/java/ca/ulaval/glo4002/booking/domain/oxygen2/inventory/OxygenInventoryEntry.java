package ca.ulaval.glo4002.booking.domain.oxygen2.inventory;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

public class OxygenInventoryEntry {

    private OxygenGrade oxygenGrade;
    private int quantity;

    public OxygenInventoryEntry(OxygenGrade oxygenGrade) {
        this.oxygenGrade = oxygenGrade;
        quantity = 0;
    }

    public void addQuantity(int quantity) {
        int newQuantity = this.quantity + quantity;
        updateQuantity(newQuantity);
    }

    public void removeQuantity(int quantity) {
        int newQuantity = this.quantity - quantity;
        updateQuantity(newQuantity);
    }

    private void updateQuantity(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("the inventory cannot have negative quantities");
        
        this.quantity = quantity;
    }

    public OxygenGrade getOxygenGrade() {
        return oxygenGrade;
    }

    public int getQuantity() {
		return quantity;
    }
}
