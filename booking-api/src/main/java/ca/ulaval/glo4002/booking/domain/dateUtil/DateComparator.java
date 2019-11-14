package ca.ulaval.glo4002.booking.domain.dateUtil;

import java.time.LocalDate;

public class DateComparator {

    public static boolean areDatesEquals(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.equals(secondDate);
    }
}
