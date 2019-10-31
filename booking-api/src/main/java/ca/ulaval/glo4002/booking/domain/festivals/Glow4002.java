package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;

public class Glow4002 {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final OffsetDateTime saleStartDate;
    private final OffsetDateTime saleEndDate;

    public Glow4002() {
        startDate = LocalDate.of(2050, 7, 17);
        endDate = LocalDate.of(2050, 7, 24);
        saleStartDate = OffsetDateTime.of(LocalDate.of(2050, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC);
        saleEndDate = OffsetDateTime.of(LocalDate.of(2050, 7, 16), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC);
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

    public boolean isDuringSaleTime(OffsetDateTime dateTime) {
        return !(dateTime.isBefore(saleStartDate) || dateTime.isAfter(saleEndDate));
    }

    public boolean isDuringEventTime(LocalDate date) {
        return !(date.isBefore(startDate) || date.isAfter(endDate));
    }

    public void validateEventDates(LocalDate startDate, LocalDate endDate) {
        if (!isDuringEventTime(startDate) || !isDuringEventTime(endDate)) {
            throw new OutOfFestivalDatesException(getStartDate(), getEndDate());
        }
    }
}
