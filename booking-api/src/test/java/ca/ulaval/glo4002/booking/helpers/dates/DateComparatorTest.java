package ca.ulaval.glo4002.booking.helpers.dates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

public class DateComparatorTest {

    private final static LocalDate DATE_START = LocalDate.now();
    private final static LocalDate DATE_END = DATE_START.plusDays(7);
    private final static OffsetDateTime DATETIME_START = OffsetDateTime.now();
    private final static OffsetDateTime DATETIME_END = DATETIME_START.plusDays(7);

    @Test
    public void givenDateOneDayBeforeStart_whenCheckingIfDateIsInclusivelyBetween_itReturnsFalse() {
        LocalDate oneDayBeforeStart = DATE_START.minusDays(1);
        boolean isDateInclusivelyBetween = DateComparator.dateIsInclusivelyBetween(oneDayBeforeStart, DATE_START, DATE_END);
        assertFalse(isDateInclusivelyBetween);
    }

    @Test
    public void givenDateOneDayAfterEnd_whenCheckingIfDateIsInclusivelyBetween_itReturnsFalse() {
        LocalDate oneDayAfterEnd = DATE_END.plusDays(1);
        boolean isDateInclusivelyBetween = DateComparator.dateIsInclusivelyBetween(oneDayAfterEnd, DATE_START, DATE_END);
        assertFalse(isDateInclusivelyBetween);
    }

    @Test
    public void givenDateIsStart_whenCheckingIfDateIsInclusivelyBetween_itReturnsTrue() {
        boolean isDateInclusivelyBetween = DateComparator.dateIsInclusivelyBetween(DATE_START, DATE_START, DATE_END);
        assertTrue(isDateInclusivelyBetween);
    }

    @Test
    public void givenDateIsEnd_whenCheckingIfDateIsInclusivelyBetween_itReturnsTrue() {
        boolean isDateInclusivelyBetween = DateComparator.dateIsInclusivelyBetween(DATE_END, DATE_START, DATE_END);
        assertTrue(isDateInclusivelyBetween);
    }

    @Test
    public void givenDateTimeOneSecondBeforeStart_whenCheckingIfDateIsInclusivelyBetween_itReturnsFalse() {
        OffsetDateTime oneSecondBeforeStart = DATETIME_START.minusSeconds(1);
        boolean isDateInclusivelyBetween = DateComparator.dateIsInclusivelyBetween(oneSecondBeforeStart, DATETIME_START, DATETIME_END);
        assertFalse(isDateInclusivelyBetween);
    }

    @Test
    public void givenDateTimeOneSecondAfterEnd_whenCheckingIfDateIsInclusivelyBetween_itReturnsFalse() {
        OffsetDateTime oneSecondAfterEnd = DATETIME_END.plusSeconds(1);
        boolean isDateInclusivelyBetween = DateComparator.dateIsInclusivelyBetween(oneSecondAfterEnd, DATETIME_START, DATETIME_END);     
        assertFalse(isDateInclusivelyBetween);
    }

    @Test
    public void givenDateTimeIsStart_whenCheckingIfDateIsInclusivelyBetween_itReturnsTrue() {
        boolean isDateInclusivelyBetween = DateComparator.dateIsInclusivelyBetween(DATETIME_START, DATETIME_START, DATETIME_END);
        assertTrue(isDateInclusivelyBetween);
    }

    @Test
    public void givenDateTimeIsEnd_whenCheckingIfDateIsInclusivelyBetween_itReturnsTrue() {
        boolean isDateInclusivelyBetween = DateComparator.dateIsInclusivelyBetween(DATETIME_END, DATETIME_START, DATETIME_END);
        assertTrue(isDateInclusivelyBetween);
    }
}