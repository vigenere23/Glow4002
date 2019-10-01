package ca.ulaval.glo4002.booking.interfaces.exceptions;

public class ApiException extends Exception {

    private static final long serialVersionUID = 1L;
    public final ApiExceptionType error;
    public final String description;

    public ApiException(ApiExceptionType error, String description) {
        super(String.format("%s : %s", error.toString(), description));
        this.error = error;
        this.description = description;
    }
}
