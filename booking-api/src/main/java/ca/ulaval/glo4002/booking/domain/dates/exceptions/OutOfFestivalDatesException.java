package ca.ulaval.glo4002.booking.domain.dates.exceptions;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.helpers.dates.DateFormatter;

public class OutOfFestivalDatesException extends RuntimeException {

    private static final long serialVersionUID = 8976216000761853983L;

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
