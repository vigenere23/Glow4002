package ca.ulaval.glo4002.booking.helpers.dates;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateCalculator {

    public static int numberOfDaysInclusivelyBetween(LocalDate startDate, LocalDate endDate) {
        Long dayCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return dayCount.intValue();
    }
}