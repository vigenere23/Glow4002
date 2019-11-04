package ca.ulaval.glo4002.booking.domain.passes.factories;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassFactory {

    private FestivalDates festival;

    public PassFactory(FestivalDates festival) {
        this.festival = festival;
    }

    public Pass create(PassOption passOption, PassCategory passCategory, LocalDate eventDate) {
        validateOptionAndEventDate(passOption, eventDate);

        switch (passOption) {
            case SINGLE_PASS:
                return new SinglePassFactory(festival).create(passCategory, eventDate);
            case PACKAGE:
                return new PackagePassFactory(festival).create(passCategory);
            default:
                throw new IllegalArgumentException(
                    String.format("No factory found for pass option %s.", passOption)
                );
        }
    }

    private void validateOptionAndEventDate(PassOption passOption, LocalDate eventDate) {
        if (passOption == PassOption.PACKAGE && eventDate != null) {
            throw new IllegalArgumentException("a package pass cannot have an event date");
        }
        if (passOption == PassOption.SINGLE_PASS && eventDate == null) {
            throw new IllegalArgumentException("a single pass must have an event date");
        }
    }
}
