package ca.ulaval.glo4002.booking.api.dtos;

import ca.ulaval.glo4002.booking.api.exceptions.ClientException;

public class ErrorDto {

    public final String error;
    public final String description;

    public ErrorDto(String error, String description) {
        this.error = error;
        this.description = description;
    }

    public ErrorDto(ClientException exception) {
        this(exception.errorType, exception.description);
    }
}
