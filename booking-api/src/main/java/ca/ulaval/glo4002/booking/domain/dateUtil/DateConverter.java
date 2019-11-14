package ca.ulaval.glo4002.booking.domain.dateUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DateConverter {

    public static OffsetDateTime toOffsetDateTimeStartOfDay(LocalDate localDate) {
        return OffsetDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    }

    public static OffsetDateTime toOffsetDateTimeEndOfDay(LocalDate localDate) {
        return OffsetDateTime.of(localDate, LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC);
    }
}
