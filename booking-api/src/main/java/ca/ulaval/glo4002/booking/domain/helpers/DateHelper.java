package ca.ulaval.glo4002.booking.domain.helpers;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public abstract class DateHelper {

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d yyyy");

    public static int daysBetweenDates(OffsetDateTime start, OffsetDateTime end) {
        return end.compareTo(end);
    }
}
