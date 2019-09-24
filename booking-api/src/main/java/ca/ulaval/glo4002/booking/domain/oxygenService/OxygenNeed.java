package ca.ulaval.glo4002.booking.domain.oxygenService;

abstract public class OxygenNeed {
    protected OxygenGrade oxygenGrade;
    protected int oxygenTankQuantity;

    public OxygenGrade getOxygenGrade() {
	return oxygenGrade;
    }

    public int getOxygenTankQuantity() {
	return oxygenTankQuantity;
    }
}
