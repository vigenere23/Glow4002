package ca.ulaval.glo4002.booking.interfaces.exceptions;

public class InvalidFormatApiException extends ApiException {
    
    private static final long serialVersionUID = -4419771761195519329L;

    public InvalidFormatApiException() {
        super(ApiExceptionType.INVALID_FORMAT, "invalid format");
    }
}
