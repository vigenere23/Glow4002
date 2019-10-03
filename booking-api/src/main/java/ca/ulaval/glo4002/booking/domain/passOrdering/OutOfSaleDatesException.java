package ca.ulaval.glo4002.booking.domain.passOrdering;

import java.time.OffsetDateTime;

public class OutOfSaleDatesException extends Exception {

    private static final long serialVersionUID = -2584063524302174944L;
    
    public OutOfSaleDatesException(OffsetDateTime festivalSaleStart, OffsetDateTime festivalSaleEnd) {
        super(String.format("order date should be between %s and %s", festivalSaleStart.toString(), festivalSaleEnd.toString()));
    }
}
