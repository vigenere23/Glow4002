package ca.ulaval.glo4002.booking.interfaces.exceptions;

public class InvalidFormatException extends ClientError {
    
    private static final long serialVersionUID = -4419771761195519329L;

    public InvalidFormatException() {
        super(ClientErrorType.INVALID_FORMAT, "invalid format");
    }
}
