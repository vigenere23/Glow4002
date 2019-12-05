package ca.ulaval.glo4002.booking.helpers.dates;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateCalculator {

    public static int numberOfDaysInclusivelyBetween(LocalDate startDate, LocalDate endDate) {
        Long dayCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return dayCount.intValue();
    }

    public static List<LocalDate> datesBetween(LocalDate start, LocalDate end) {
        List<LocalDate> dates = new ArrayList<>();
        while(!start.isAfter(end)) {
            dates.add(start);
            start = start.plusDays(1);
        }
        return dates;
    }
}
