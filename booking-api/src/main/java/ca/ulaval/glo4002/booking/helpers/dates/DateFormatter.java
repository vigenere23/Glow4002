package ca.ulaval.glo4002.booking.helpers.dates;

import java.time.format.DateTimeFormatter;

public abstract class DateFormatter {

    public static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
}
