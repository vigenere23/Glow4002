package ca.ulaval.glo4002.booking.interfaces.rest.exceptions;

public class InvalidOrderDateException extends ClientError {

    public InvalidOrderDateException(String description) {
        super(ClientErrorType.INVALID_ORDER_DATE, description);
    }
}