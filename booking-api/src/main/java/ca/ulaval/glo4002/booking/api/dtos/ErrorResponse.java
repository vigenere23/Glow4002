package ca.ulaval.glo4002.booking.api.dtos;

import ca.ulaval.glo4002.booking.api.exceptions.ClientError;
import ca.ulaval.glo4002.booking.api.exceptions.ClientErrorType;

public class ErrorResponse {

    public final ClientErrorType error;
    public final String description;

    public ErrorResponse(ClientErrorType error, String description) {
        this.error = error;
        this.description = description;
    }

    public ErrorResponse(ClientError exception) {
        this(exception.errorType, exception.description);
    }
}
