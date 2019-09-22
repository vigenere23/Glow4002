package ca.ulaval.glo4002.booking.domain.oxygenService;

import ca.ulaval.glo4002.booking.domain.enumeration.PassCategory;

public class OxygenNeed implements GasNeedable {
    private OxygenGrade oxygenGrade;
    private int oxygenTankQuantity;

    public OxygenNeed() {
    }

    public OxygenNeed(OxygenGrade oxygenGrade, int oxygenTankQuantity) {
	this.oxygenGrade = oxygenGrade;
	this.oxygenTankQuantity = oxygenTankQuantity;
    }

    public void setNeedsBasedOnPassCategory(PassCategory passCategory) {
	if (passCategory == PassCategory.SUPERNOVA) {
	    oxygenGrade = OxygenGrade.E;
	    oxygenTankQuantity = 5;
	} else if (passCategory == PassCategory.SUPERGIANT) {
	    oxygenGrade = OxygenGrade.B;
	    oxygenTankQuantity = 3;
	} else if (passCategory == PassCategory.NEBULA) {
	    oxygenGrade = OxygenGrade.A;
	    oxygenTankQuantity = 3;
	} else {
	    throw new IllegalArgumentException(
		    String.format("No oxygen need impemented for category %s.", passCategory));
	}
    }

    public OxygenGrade getOxygenGrade() {
	return oxygenGrade;
    }

    public int getOxygenTankQuantity() {
	return oxygenTankQuantity;
    }
}
