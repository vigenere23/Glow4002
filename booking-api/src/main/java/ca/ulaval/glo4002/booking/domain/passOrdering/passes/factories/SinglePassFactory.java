package ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories;

import java.time.LocalDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupernovaSinglePass;

public class SinglePassFactory {

    // TODO include the OxygenService here once it exists
    public SinglePass newPass(PassCategory passCategory, LocalDateTime eventDate) {
        switch(passCategory) {
            case NEBULA:
                return new NebulaSinglePass(eventDate);
            case SUPERGIANT:
                return new SupergiantSinglePass(eventDate);
            case SUPERNOVA:
                return new SupernovaSinglePass(eventDate);
            default:
                throw new IllegalArgumentException(
                    String.format("No single pass impemented for category %s.", passCategory)
                );
        }
    }
}
