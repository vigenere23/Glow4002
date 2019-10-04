package ca.ulaval.glo4002.booking.interfaces.exceptions;

public class InvalidEventDateException extends ClientError {

    private static final long serialVersionUID = 4791337343374627024L;
    
    public InvalidEventDateException(String description) {
        super(ClientErrorType.INVALID_EVENT_DATE, description);
    }
}
