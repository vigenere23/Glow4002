package ca.ulaval.glo4002.booking.interfaces.exceptions;

public abstract class ClientError extends Exception {

    public final ClientErrorType error;
    public final String description;
    public final int status;

    public ClientError(int status, ClientErrorType error, String description) {
        super(String.format("%s : %s", error.toString(), description));
        this.status = status;
        this.error = error;
        this.description = description;
    }

    public ClientError(ClientErrorType error, String description) {
        this(400, error, description);
    }
}
