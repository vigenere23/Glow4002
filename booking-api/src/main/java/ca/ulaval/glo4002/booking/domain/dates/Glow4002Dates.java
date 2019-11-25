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

    private LocalDate startDate;
    private LocalDate endDate;

    public Glow4002Dates() {
        updateStartDate(LocalDate.of(2050, 7, 17));
        updateEndDate(LocalDate.of(2050, 7, 24));
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
    public void updateStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public void updateEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public int getNumberOfFestivalDays() {
        return DateCalculator.numberOfDaysInclusivelyBetween(startDate, endDate);
    }

    @Override
    public OffsetDateTime getSaleStartDate() {
        return DateConverter.toOffsetDateTimeStartOfDay(startDate.minusDays(180));
    }

    @Override
    public OffsetDateTime getSaleEndDate() {
        return DateConverter.toOffsetDateTimeEndOfDay(startDate.minusDays(1));
    }

    @Override
    public LocalDate getOxygenLimitDeliveryDate() {
        return startDate.minusDays(1);
    }

    @Override
    public boolean isDuringSaleTime(OffsetDateTime orderDate) {
        return DateComparator.dateIsInclusivelyBetween(orderDate, getSaleStartDate(), getSaleEndDate());
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
            throw new OutOfSaleDatesException(getSaleStartDate(), getSaleEndDate());
        }
    }
}
