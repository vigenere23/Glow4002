package ca.ulaval.glo4002.booking.domain.dates.exceptions;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.helpers.dates.DateFormatter;

public class OutOfSaleDatesException extends RuntimeException {
    
    private static final long serialVersionUID = -6174561766860626250L;

    public OutOfSaleDatesException(OffsetDateTime festivalSaleStart, OffsetDateTime festivalSaleEnd) {
        super(
            String.format(
                "order date should be between %s and %s",
                festivalSaleStart.format(DateFormatter.outputFormatter),
                festivalSaleEnd.format(DateFormatter.outputFormatter)
            )
        );
    }
}
