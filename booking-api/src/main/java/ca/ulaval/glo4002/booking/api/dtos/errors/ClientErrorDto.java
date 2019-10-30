package ca.ulaval.glo4002.booking.api.dtos.errors;

import ca.ulaval.glo4002.booking.api.exceptions.ClientException;

public class ClientErrorDto {

    public final String error;
    public final String description;

    public ClientErrorDto(String error, String description) {
        this.error = error;
        this.description = description;
    }

    public ClientErrorDto(ClientException exception) {
        this(exception.errorType, exception.description);
    }
}
