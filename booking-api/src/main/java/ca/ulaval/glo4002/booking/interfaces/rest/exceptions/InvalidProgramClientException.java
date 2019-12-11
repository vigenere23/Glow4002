package ca.ulaval.glo4002.booking.interfaces.rest.exceptions;

public class InvalidProgramClientException extends ClientException {
    
    private static final long serialVersionUID = 7834943105652644826L;

    public InvalidProgramClientException() {
        super("INVALID_PROGRAM", "the program is invalid");
    }
}
