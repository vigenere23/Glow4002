package ca.ulaval.glo4002.booking.api.exceptions;

public class InvalidFormatException extends ClientError {
    
    public InvalidFormatException() {
        super("INVALID_FORMAT", "invalid format");
    }
}
