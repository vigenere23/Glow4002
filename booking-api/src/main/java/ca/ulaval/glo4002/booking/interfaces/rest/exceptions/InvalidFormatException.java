package ca.ulaval.glo4002.booking.interfaces.rest.exceptions;

public class InvalidFormatException extends ClientException {
    
    private static final long serialVersionUID = 7834943105652644826L;

    public InvalidFormatException() {
        super("INVALID_FORMAT", "invalid format");
    }
}
