package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Glow4002 {

    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;
    private final OffsetDateTime saleStartDate;
    private final OffsetDateTime saleEndDate;

    public Glow4002() {
        startDate = OffsetDateTime.of(LocalDate.of(2050, 7, 17), LocalTime.MIDNIGHT, ZoneOffset.UTC);
        endDate = OffsetDateTime.of(LocalDate.of(2050, 7, 24), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC);
        saleStartDate = OffsetDateTime.of(LocalDate.of(2050, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC);
        saleEndDate = OffsetDateTime.of(LocalDate.of(2050, 7, 16), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC);
    }

    public OffsetDateTime getStartDate() {
        return this.startDate;
    }

    public OffsetDateTime getEndDate() {
        return this.endDate;
    }

    public OffsetDateTime getSaleStartDate() {
        return this.saleStartDate;
    }

    public OffsetDateTime getSaleEndDate() {
        return this.saleEndDate;
    }

    public boolean isDuringSaleTime(OffsetDateTime dateTime) {
        return dateTime.isAfter(this.saleStartDate) && dateTime.isBefore(this.saleEndDate);
    }

    public boolean isDuringEventTime(OffsetDateTime dateTime) {
        return dateTime.isAfter(this.startDate) && dateTime.isBefore(this.endDate);
    }
}
