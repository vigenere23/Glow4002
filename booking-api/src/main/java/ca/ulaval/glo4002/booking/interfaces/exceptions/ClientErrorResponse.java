package ca.ulaval.glo4002.booking.interfaces.exceptions;

public class ClientErrorResponse {

    public final ClientErrorType error;
    public final String description;

    public ClientErrorResponse(ClientErrorType error, String description) {
        this.error = error;
        this.description = description;
    }

    public ClientErrorResponse(ClientError exception) {
        this(exception.error, exception.description);
    }
}
