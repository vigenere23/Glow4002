package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.dateUtil.DateComparator;
import ca.ulaval.glo4002.booking.domain.dateUtil.DateConverter;;

public class Glow4002Dates implements FestivalDates {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalDate programRevealDate;
    private final OffsetDateTime saleStartDate;
    private final OffsetDateTime saleEndDate;

    public Glow4002Dates() {
        startDate = LocalDate.of(2050, 7, 17);
        endDate = LocalDate.of(2050, 7, 24);
        programRevealDate = LocalDate.of(2050, 07, 12);
        saleStartDate = DateConverter.toOffsetDateTimeStartOfDay(LocalDate.of(2050, 1, 1));
        saleEndDate = DateConverter.toOffsetDateTimeEndOfDay(LocalDate.of(2050, 7, 16));
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getProgramRevealDate() {
        return programRevealDate;
    }

    public LocalDate getOxygenLimitDeliveryDate() {
        return startDate.minusDays(1);
     }

    public OffsetDateTime getSaleStartDate() {
        return saleStartDate;
    }

    public OffsetDateTime getSaleEndDate() {
        return saleEndDate;
    }

    public boolean isDuringSaleTime(OffsetDateTime orderDate) {
        return DateComparator.dateIsInclusivelyBetween(orderDate, saleStartDate, saleEndDate);
    }

    public boolean isDuringEventTime(LocalDate date) {
        return DateComparator.dateIsInclusivelyBetween(date, startDate, endDate);
    }

    public void validateEventDate(LocalDate localDate) {
        if (!isDuringEventTime(localDate)) {
            throw new OutOfFestivalDatesException(startDate, endDate);
        }
    }

    public void validateOrderDate(OffsetDateTime orderDate) {
        if (!isDuringSaleTime(orderDate)) {
            throw new OutOfSaleDatesException(saleStartDate, saleEndDate);
        }
    }
}
