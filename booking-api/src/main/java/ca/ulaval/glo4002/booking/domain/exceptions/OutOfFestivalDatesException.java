package ca.ulaval.glo4002.booking.domain.exceptions;

import java.time.OffsetDateTime;

public class OutOfFestivalDatesException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public OutOfFestivalDatesException(OffsetDateTime festivalStart, OffsetDateTime festivalEnd) {
        super(String.format("event dates should be between %s and %s", festivalStart.toString(), festivalEnd.toString()));
    }
}
