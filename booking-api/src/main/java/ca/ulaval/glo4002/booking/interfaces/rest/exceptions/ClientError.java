package ca.ulaval.glo4002.booking.interfaces.rest.exceptions;

public abstract class ClientError extends Exception {

    public final ClientErrorType errorType;
    public final String description;
    public final int status;

    public ClientError(int status, ClientErrorType errorType, String description) {
        super(String.format("%s : %s", errorType.toString(), description));
        this.status = status;
        this.errorType = errorType;
        this.description = description;
    }

    public ClientError(ClientErrorType error, String description) {
        this(400, error, description);
    }
}
