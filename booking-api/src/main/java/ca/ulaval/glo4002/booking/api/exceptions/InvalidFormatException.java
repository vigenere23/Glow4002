package ca.ulaval.glo4002.booking.api.exceptions;

public class InvalidFormatException extends ClientException {
    
    public InvalidFormatException(Exception exception, String message) {
        super("INVALID_FORMAT : ", "invalid format, ".concat(message).concat(" The original exception is : ").concat(exception.getMessage()));
    }
}
