package ca.ulaval.glo4002.booking.domain.dates;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.dates.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.dates.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.helpers.dates.DateCalculator;
import ca.ulaval.glo4002.booking.helpers.dates.DateComparator;
import ca.ulaval.glo4002.booking.helpers.dates.DateConverter;

public class Glow4002Dates implements FestivalDates, OxygenDates {

    private LocalDate startDate;
    private LocalDate endDate;

    public Glow4002Dates() {
        this(LocalDate.of(2050, 7, 17), LocalDate.of(2050, 7, 24));
    }

    public Glow4002Dates(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
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
    public List<LocalDate> getFestivalDays() {
        return DateCalculator.datesBetween(startDate, endDate);
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
    public OffsetDateTime getOxygenLimitDeliveryDate() {
        return DateConverter.toOffsetDateTimeEndOfDay(startDate.minusDays(1));
    }

    @Override
    public void validateEventDate(LocalDate localDate) {
        boolean isDuringFestival = DateComparator.dateIsInclusivelyBetween(localDate, startDate, endDate);
        if (!isDuringFestival) {
            throw new OutOfFestivalDatesException(startDate, endDate);
        }
    }

    @Override
    public void validateOrderDate(OffsetDateTime orderDate) {
        boolean isDuringSale = DateComparator.dateIsInclusivelyBetween(orderDate, getSaleStartDate(), getSaleEndDate());
        if (!isDuringSale) {
            throw new OutOfSaleDatesException(getSaleStartDate(), getSaleEndDate());
        }
    }
}
