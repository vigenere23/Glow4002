package ca.ulaval.glo4002.booking.domain.passOrdering;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OutOfSaleDatesException extends Exception {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy");
    
    public OutOfSaleDatesException(OffsetDateTime festivalSaleStart, OffsetDateTime festivalSaleEnd) {
        super(
            String.format(
                "order date should be between %s and %s",
                festivalSaleStart.format(formatter),
                festivalSaleEnd.format(formatter)
            )
        );
    }
}
