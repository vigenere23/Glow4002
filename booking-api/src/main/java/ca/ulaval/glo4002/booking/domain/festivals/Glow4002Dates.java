package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.helpers.DateConverter;;

public class Glow4002Dates implements FestivalDates {

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public OffsetDateTime getSaleStartDate() {
        return saleStartDate;
    }

    public OffsetDateTime getSaleEndDate() {
        return saleEndDate;
    }

    public boolean isDuringSaleTime(OffsetDateTime orderDate) {
        return !(orderDate.isBefore(saleStartDate) || orderDate.isAfter(saleEndDate));
    }

    public boolean isDuringEventTime(LocalDate date) {
        return !(date.isBefore(startDate) || date.isAfter(endDate));
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
