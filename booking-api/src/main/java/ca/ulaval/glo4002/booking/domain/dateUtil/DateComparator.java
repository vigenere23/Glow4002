package ca.ulaval.glo4002.booking.domain.dateUtil;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class DateComparator {

    public static boolean dateIsInclusivelyBetween(LocalDate date, LocalDate min, LocalDate max) {
        return !(date.isBefore(min) || date.isAfter(max));
    }

    public static boolean dateIsInclusivelyBetween(OffsetDateTime date, OffsetDateTime min, OffsetDateTime max) {
        return !(date.isBefore(min) || date.isAfter(max));
    }
}
