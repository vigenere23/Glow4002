package ca.ulaval.glo4002.booking.domain.passOrdering;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.helpers.DateHelper;

public class OutOfSaleDatesException extends Exception {
    
    public OutOfSaleDatesException(OffsetDateTime festivalSaleStart, OffsetDateTime festivalSaleEnd) {
        super(
            String.format(
                "order date should be between %s and %s",
                festivalSaleStart.format(DateHelper.dateFormatter),
                festivalSaleEnd.format(DateHelper.dateFormatter)
            )
        );
    }
}
