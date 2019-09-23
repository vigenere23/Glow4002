package ca.ulaval.glo4002.booking.domain.oxygenService;

import ca.ulaval.glo4002.booking.domain.passOrdering.PassCategory;

public class OxygenNeedFactory {
    public OxygenNeed getOxygenNeed(PassCategory passCategory) {
	switch (passCategory) {
	case SUPERNOVA:
	    return new OxygenGradeENeed();
	case SUPERGIANT:
	    return new OxygenGradeBNeed();
	case NEBULA:
	    return new OxygenGradeANeed();
	default:
	    throw new IllegalArgumentException(
		    String.format("No oxygen need impemented for category %s.", passCategory));
	}
    }
}
