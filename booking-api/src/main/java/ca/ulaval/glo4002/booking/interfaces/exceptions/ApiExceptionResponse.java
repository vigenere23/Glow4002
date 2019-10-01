package ca.ulaval.glo4002.booking.interfaces.exceptions;

public class ApiExceptionResponse {

    public final ApiExceptionType error;
    public final String description;

    public ApiExceptionResponse(ApiExceptionType error, String description) {
        this.error = error;
        this.description = description;
    }

    public ApiExceptionResponse(ApiException exception) {
        this(exception.error, exception.description);
    }
}
