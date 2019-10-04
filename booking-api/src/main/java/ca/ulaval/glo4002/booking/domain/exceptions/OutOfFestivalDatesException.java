package ca.ulaval.glo4002.booking.domain.exceptions;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OutOfFestivalDatesException extends Exception {

    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");

    public OutOfFestivalDatesException(OffsetDateTime festivalStart, OffsetDateTime festivalEnd) {
        super(
            String.format(
                "event dates should be between %s and %s",
                festivalStart.format(formatter),
                festivalEnd.format(formatter)
            )
        );
    }
}
