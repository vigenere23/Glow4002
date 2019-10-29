package ca.ulaval.glo4002.booking.api.exceptions;

public abstract class ClientError extends RuntimeException {

    public final String errorType;
    public final String description;
    public final int status;

    public ClientError(int status, String errorType, String description) {
        super(String.format("%s : %s", errorType.toString(), description));
        this.status = status;
        this.errorType = errorType;
        this.description = description;
    }

    public ClientError(String error, String description) {
        this(400, error, description);
    }
}
