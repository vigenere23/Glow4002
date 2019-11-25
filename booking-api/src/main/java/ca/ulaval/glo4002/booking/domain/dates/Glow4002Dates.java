package ca.ulaval.glo4002.booking.domain.dates;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.helpers.dates.DateCalculator;
import ca.ulaval.glo4002.booking.helpers.dates.DateComparator;
import ca.ulaval.glo4002.booking.helpers.dates.DateConverter;;

public class Glow4002Dates implements FestivalDates, OxygenDates {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final OffsetDateTime saleStartDate;
    private final OffsetDateTime saleEndDate;

    public Glow4002Dates() {
        startDate = LocalDate.of(2050, 7, 17);
        endDate = LocalDate.of(2050, 7, 24);
        saleStartDate = DateConverter.toOffsetDateTimeStartOfDay(LocalDate.of(2050, 1, 1));
        saleEndDate = DateConverter.toOffsetDateTimeEndOfDay(LocalDate.of(2050, 7, 16));
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public int getNumberOfDays() {
        return DateCalculator.numberOfDaysInclusivelyBetween(startDate, endDate);
    }

    @Override
    public LocalDate getOxygenLimitDeliveryDate() {
        return startDate.minusDays(1);
    }

    @Override
    public OffsetDateTime getSaleStartDate() {
        return saleStartDate;
    }

    @Override
    public OffsetDateTime getSaleEndDate() {
        return saleEndDate;
    }

    @Override
    public boolean isDuringSaleTime(OffsetDateTime orderDate) {
        return DateComparator.dateIsInclusivelyBetween(orderDate, saleStartDate, saleEndDate);
    }

    @Override
    public boolean isDuringEventTime(LocalDate date) {
        return DateComparator.dateIsInclusivelyBetween(date, startDate, endDate);
    }

    @Override
    public void validateEventDate(LocalDate localDate) {
        if (!isDuringEventTime(localDate)) {
            throw new OutOfFestivalDatesException(startDate, endDate);
        }
    }

    @Override
    public void validateOrderDate(OffsetDateTime orderDate) {
        if (!isDuringSaleTime(orderDate)) {
            throw new OutOfSaleDatesException(saleStartDate, saleEndDate);
        }
    }
}
