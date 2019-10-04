package ca.ulaval.glo4002.booking.interfaces.exceptions;

public class ErrorDto {

    public final ClientErrorType errorType;
    public final String description;

    public ErrorDto(ClientErrorType errorType, String description) {
        this.errorType = errorType;
        this.description = description;
    }

    public ErrorDto(ClientError exception) {
        this(exception.errorType, exception.description);
    }
}
