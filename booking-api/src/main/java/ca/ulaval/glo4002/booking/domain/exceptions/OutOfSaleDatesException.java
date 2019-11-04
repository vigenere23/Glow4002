package ca.ulaval.glo4002.booking.domain.exceptions;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.helpers.DateFormatter;

public class OutOfSaleDatesException extends RuntimeException {
    
    public OutOfSaleDatesException(OffsetDateTime festivalSaleStart, OffsetDateTime festivalSaleEnd) {
        super(
            String.format(
                "order date should be between %s and %s",
                festivalSaleStart.format(DateFormatter.formatter),
                festivalSaleEnd.format(DateFormatter.formatter)
            )
        );
    }
}
