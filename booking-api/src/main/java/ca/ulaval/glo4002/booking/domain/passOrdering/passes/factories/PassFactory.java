package ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public class PassFactory {

    private Glow4002 festival;

    public PassFactory(Glow4002 festival) {
        this.festival = festival;
    }

    public Pass create(PassOption passOption, PassCategory passCategory, LocalDate eventDate) throws OutOfFestivalDatesException {
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
