package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Glow4002 extends Festival {

    public Glow4002() {
        super(
            OffsetDateTime.of(LocalDate.of(2050, 7, 17), LocalTime.MIDNIGHT, ZoneOffset.UTC),
            OffsetDateTime.of(LocalDate.of(2050, 7, 25), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC),
            OffsetDateTime.of(LocalDate.of(2050, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC),
            OffsetDateTime.of(LocalDate.of(2050, 7, 17), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC)
        );
    }
}
