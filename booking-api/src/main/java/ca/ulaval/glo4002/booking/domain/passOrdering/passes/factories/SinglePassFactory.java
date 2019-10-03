package ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupernovaSinglePass;

public class SinglePassFactory {
    private OffsetDateTime festivalStart;
    private OffsetDateTime festivalEnd;

    public SinglePassFactory(OffsetDateTime festivalStart, OffsetDateTime festivalEnd) {
        this.festivalStart = festivalStart;
        this.festivalEnd = festivalEnd;
    }

    private void validateEventDate(OffsetDateTime eventDate) {
        if (eventDate.isBefore(festivalStart) || eventDate.isAfter(festivalEnd)) {
            throw new IllegalArgumentException(
                String.format(
                    "event date should be between %s and %s",
                    this.festivalStart.format(DateTimeFormatter.ofPattern("LL d u")),
                    this.festivalEnd.format(DateTimeFormatter.ofPattern("LL d u"))
                )
            );
        }
    }

    public SinglePass create(PassCategory passCategory, OffsetDateTime eventDate) {
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
}
