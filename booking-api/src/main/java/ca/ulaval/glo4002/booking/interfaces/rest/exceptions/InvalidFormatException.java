package ca.ulaval.glo4002.booking.interfaces.rest.exceptions;

public class InvalidFormatException extends ClientException {
    
    public InvalidFormatException() {
        super("INVALID_FORMAT", "invalid format");
    }
}
