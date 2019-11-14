package ca.ulaval.glo4002.booking.domain.dateUtil;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateCalculatorTest {

    @Test
    public void givenTwoTimesSameDay_whenCalculatingNumberOfDaysInclusivelyBetween_itReturnsOne() {
        LocalDate date = LocalDate.now();
        int numberOfDays = DateCalculator.numberOfDaysInclusivelyBetween(date, date);
        assertEquals(1, numberOfDays);
    }

    @Test
    public void givenOneDayDifference_whenCalculatingNumberOfDaysInclusivelyBetween_itReturnsTwo() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        int numberOfDays = DateCalculator.numberOfDaysInclusivelyBetween(startDate, endDate);
        assertEquals(2, numberOfDays);
    }
}