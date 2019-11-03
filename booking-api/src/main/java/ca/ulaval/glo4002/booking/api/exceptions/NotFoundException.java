package ca.ulaval.glo4002.booking.api.exceptions;

public class NotFoundException extends ClientException {

    public NotFoundException(String objectType, String objectNumber) {
        super(
            404,
            String.format("%s_NOT_FOUND", objectType.toUpperCase()),
            String.format("%s with number %s not found", objectType, objectNumber)
        );
    }
}
