package ca.ulaval.glo4002.booking.interfaces.rest.exceptions;

public class InvalidEventDateException extends ClientError {

    public InvalidEventDateException(String description) {
        super(ClientErrorType.INVALID_EVENT_DATE, description);
    }
}