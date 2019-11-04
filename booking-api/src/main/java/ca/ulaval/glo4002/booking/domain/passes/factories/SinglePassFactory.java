package ca.ulaval.glo4002.booking.domain.passes.factories;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SinglePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupergiantSinglePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupernovaSinglePass;

public class SinglePassFactory {
    private FestivalDates festival;

    public SinglePassFactory(FestivalDates festival) {
        this.festival = festival;
    }

    public SinglePass create(PassCategory passCategory, LocalDate eventDate) throws OutOfFestivalDatesException {
        validateEventDate(eventDate);

        switch (passCategory) {
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

    private void validateEventDate(LocalDate eventDate) throws OutOfFestivalDatesException {
        if (!festival.isDuringEventTime(eventDate)) {
            throw new OutOfFestivalDatesException(festival.getStartDate(), festival.getEndDate());
        }
    }
}
