package ca.ulaval.glo4002.booking.interfaces.rest.exceptions;

public class InvalidFormatException extends ClientError {
    
    public InvalidFormatException() {
        super(ClientErrorType.INVALID_FORMAT, "invalid format");
    }
}
