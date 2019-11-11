package ca.ulaval.glo4002.booking.domain.exceptions;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.helpers.DateFormatter;

public class OutOfFestivalDatesException extends RuntimeException {

    public OutOfFestivalDatesException(LocalDate festivalStart, LocalDate festivalEnd) {
        super(
            String.format(
                "event date should be between %s and %s",
                festivalStart.format(DateFormatter.outputFormatter),
                festivalEnd.format(DateFormatter.outputFormatter)
            )
        );
    }
}
