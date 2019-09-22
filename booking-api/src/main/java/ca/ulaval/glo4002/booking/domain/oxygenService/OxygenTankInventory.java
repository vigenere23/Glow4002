package ca.ulaval.glo4002.booking.domain.oxygenService;

public class OxygenTankInventory {
    private int totalQuantity = 0;
    private OxygenGrade oxygenGrade;

    public OxygenTankInventory(OxygenGrade oxygenCategory) {
	this.oxygenGrade = oxygenCategory;
    }

    public void adjustInventory(OxygenNeed oxygenNeed) {
	totalQuantity += oxygenNeed.getOxygenTankQuantity();
    }

    public int getInventory() {
	return totalQuantity;
    }

    public OxygenGrade getOxygenGrade() {
	return oxygenGrade;
    }
}
