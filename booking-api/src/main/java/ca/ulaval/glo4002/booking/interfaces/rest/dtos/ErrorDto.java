package ca.ulaval.glo4002.booking.interfaces.rest.dtos;

import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.ClientError;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.ClientErrorType;

public class ErrorDto {

    public final ClientErrorType error;
    public final String description;

    public ErrorDto(ClientErrorType error, String description) {
        this.error = error;
        this.description = description;
    }

    public ErrorDto(ClientError exception) {
        this(exception.errorType, exception.description);
    }
}
