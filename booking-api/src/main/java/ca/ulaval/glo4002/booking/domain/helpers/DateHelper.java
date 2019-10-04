package ca.ulaval.glo4002.booking.domain.helpers;

import java.time.format.DateTimeFormatter;

public abstract class DateHelper {

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
}
