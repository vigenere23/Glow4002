package ca.ulaval.glo4002.booking.domain.dateUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateCalculator {

    public static int daysBetween(LocalDate startDate, LocalDate endDate) {
        Long dayCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return dayCount.intValue();
    }
}
