package ca.ulaval.glo4002.booking.api.dtos;

import ca.ulaval.glo4002.booking.api.exceptions.ClientException;

public class ErrorResponse {

    public final String error;
    public final String description;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }

    public ErrorResponse(ClientException exception) {
        this(exception.errorType, exception.description);
    }
}
