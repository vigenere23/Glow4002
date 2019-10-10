package ca.ulaval.glo4002.booking.api.exceptions;

public class InvalidEventDateException extends ClientError {

    public InvalidEventDateException(String description) {
        super(ClientErrorType.INVALID_EVENT_DATE, description);
    }
}