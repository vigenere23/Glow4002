package ca.ulaval.glo4002.booking.interfaces.rest.exceptions;

public class InvalidFormatClientException extends ClientException {
    
    private static final long serialVersionUID = 7834943105652644826L;

    public InvalidFormatClientException() {
        super("INVALID_FORMAT", "invalid format");
    }
}
