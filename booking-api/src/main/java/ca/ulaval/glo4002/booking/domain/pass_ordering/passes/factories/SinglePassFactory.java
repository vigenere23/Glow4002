package ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories;

import java.time.LocalDateTime;

import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.SinglePass;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.SuperGiantSinglePass;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.SuperNovaSinglePass;

public class SinglePassFactory {

    public SinglePass newPass(PassCategory passCategory, LocalDateTime eventDate) {
        switch(passCategory) {
            case NEBULA:
                return new NebulaSinglePass(eventDate);
            case SUPERGIANT:
                return new SuperGiantSinglePass(eventDate);
            case SUPERNOVA:
                return new SuperNovaSinglePass(eventDate);
            default:
                throw new IllegalArgumentException(
                    String.format("No single pass impemented for category %s.", passCategory)
                );
        }
    }
}
