package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Glow4002Festival extends Festival {

    public Glow4002Festival() {
        super(
            OffsetDateTime.of(2050, 7, 17, 0, 0, 0, 0, ZoneOffset.ofHours(0)),
            OffsetDateTime.of(2050, 7, 24, 0, 0, 0, 0, ZoneOffset.ofHours(0)),
            OffsetDateTime.of(2050, 1, 1, 0, 0, 0, 0, ZoneOffset.ofHours(0)),
            OffsetDateTime.of(2050, 7, 24, 0, 0, 0, 0, ZoneOffset.ofHours(0))
        );
    }

    public boolean isEventTime(OffsetDateTime eventDate) {
        return eventDate.isAfter(this.startDate) && eventDate.isBefore(this.endDate);
    }

    public boolean isSaleTime(OffsetDateTime eventDate) {
        return eventDate.isAfter(this.saleStartDate) && eventDate.isBefore(this.saleEndDate);
    }
}
