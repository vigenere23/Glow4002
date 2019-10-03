package ca.ulaval.glo4002.booking.interfaces.exceptions;

public class InvalidOrderDateException extends ClientError {

    private static final long serialVersionUID = 4791337343374627024L;
    
    public InvalidOrderDateException(String description) {
        super(ClientErrorType.INVALID_ORDER_DATE, description);
    }
}