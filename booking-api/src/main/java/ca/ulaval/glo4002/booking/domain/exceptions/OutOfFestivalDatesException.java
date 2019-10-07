package ca.ulaval.glo4002.booking.domain.exceptions;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.helpers.DateHelper;

public class OutOfFestivalDatesException extends Exception {

    public OutOfFestivalDatesException(LocalDate festivalStart, LocalDate festivalEnd) {
        super(
            String.format(
                "event date should be between %s and %s",
                festivalStart.format(DateHelper.dateFormatter),
                festivalEnd.format(DateHelper.dateFormatter)
            )
        );
    }
}
