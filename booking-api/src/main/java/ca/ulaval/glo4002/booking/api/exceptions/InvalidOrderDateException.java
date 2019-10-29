package ca.ulaval.glo4002.booking.api.exceptions;

public class InvalidOrderDateException extends ClientError {

    public InvalidOrderDateException(String description) {
        super("INVALID_ORDER_DATE", description);
    }
}