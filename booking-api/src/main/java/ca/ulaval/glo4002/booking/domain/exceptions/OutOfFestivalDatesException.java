package ca.ulaval.glo4002.booking.domain.exceptions;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.helpers.DateHelper;

public class OutOfFestivalDatesException extends Exception {

    public OutOfFestivalDatesException(OffsetDateTime festivalStart, OffsetDateTime festivalEnd) {
        super(
            String.format(
                "event dates should be between %s and %s",
                festivalStart.format(DateHelper.dateFormatter),
                festivalEnd.format(DateHelper.dateFormatter)
            )
        );
    }
}
