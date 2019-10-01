package ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public class PassFactory {

    private OffsetDateTime festivalStart;
    private OffsetDateTime festivalEnd;

    public PassFactory(OffsetDateTime festivalStart, OffsetDateTime festivalEnd) {
        this.festivalStart = festivalStart;
        this.festivalEnd = festivalEnd;
    }

    public Pass create(PassOption passOption, PassCategory passCategory, OffsetDateTime eventDate) {
        switch (passOption) {
            case SINGLE_PASS:
                return new SinglePassFactory().create(passCategory, eventDate);
            case PACKAGE:
                return new PackagePassFactory(festivalStart, festivalEnd).create(passCategory);
            default:
                throw new IllegalArgumentException(
                    String.format("No factory found for pass option %s.", passOption)
                );
        }
    }
}
