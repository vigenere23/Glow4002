package ca.ulaval.glo4002.booking.domain.oxygenService;

import ca.ulaval.glo4002.booking.domain.passOrdering.PassCategory;

public class OxygenNeedFactory {
    public OxygenNeed getOxygenNeed(PassCategory passCategory) {
	if (passCategory == PassCategory.SUPERNOVA) {
	    return new OxygenGradeENeed();
	} else if (passCategory == PassCategory.SUPERGIANT) {
	    return new OxygenGradeBNeed();
	} else if (passCategory == PassCategory.NEBULA) {
	    return new OxygenGradeANeed();
	} else {
	    throw new IllegalArgumentException(
		    String.format("No oxygen need impemented for category %s.", passCategory));
	}
    }
}
