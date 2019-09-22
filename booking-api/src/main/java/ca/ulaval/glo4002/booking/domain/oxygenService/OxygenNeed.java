package ca.ulaval.glo4002.booking.domain.oxygenService;

import ca.ulaval.glo4002.booking.domain.enumeration.PassCategory;

public class OxygenNeed implements GasNeedable {
    private OxygenGrade oxygenGrade;
    private int oxygenBottleQuantity;

    public void initialize(PassCategory passCategory) {
	if (passCategory == PassCategory.SUPERNOVA) {
	    oxygenGrade = OxygenGrade.E;
	    oxygenBottleQuantity = 5;
	} else if (passCategory == PassCategory.SUPERGIANT) {
	    oxygenGrade = OxygenGrade.B;
	    oxygenBottleQuantity = 3;
	} else if (passCategory == PassCategory.NEBULA) {
	    oxygenGrade = OxygenGrade.A;
	    oxygenBottleQuantity = 3;
	} else {
	    throw new IllegalArgumentException(
		    String.format("No oxygen need impemented for category %s.", passCategory));
	}
    }

    public OxygenGrade getOxygenGrade() {
	return oxygenGrade;
    }

    public int getOxygenBottleQuantity() {
	return oxygenBottleQuantity;
    }

}
