package ca.ulaval.glo4002.booking.interfaces.rest.exceptions;

public abstract class ClientException extends RuntimeException {

    public final String errorType;
    public final String description;
    public final int status;

    public ClientException(int status, String errorType, String description) {
        super(String.format("%s : %s", errorType.toString(), description));
        this.status = status;
        this.errorType = errorType;
        this.description = description;
    }

    public ClientException(String error, String description) {
        this(400, error, description);
    }
}
