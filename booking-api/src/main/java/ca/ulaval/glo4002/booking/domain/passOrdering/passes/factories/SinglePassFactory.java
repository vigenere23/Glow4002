package ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupernovaSinglePass;

public class SinglePassFactory {
    private Glow4002 festival;

    public SinglePassFactory(Glow4002 festival) {
        this.festival = festival;
    }

    public SinglePass create(PassCategory passCategory, OffsetDateTime eventDate) throws OutOfFestivalDatesException {
        validateEventDate(eventDate);

        switch(passCategory) {
            case NEBULA:
                return new NebulaSinglePass(eventDate);
            case SUPERGIANT:
                return new SupergiantSinglePass(eventDate);
            case SUPERNOVA:
                return new SupernovaSinglePass(eventDate);
            default:
                throw new IllegalArgumentException(
                    String.format("No single pass implemented for category %s.", passCategory)
                );
        }
    }

    private void validateEventDate(OffsetDateTime eventDate) throws OutOfFestivalDatesException {
        if (!this.festival.isDuringEventTime(eventDate)) {
            throw new OutOfFestivalDatesException(this.festival.getStartDate(), this.festival.getEndDate());
        }
    }
}
