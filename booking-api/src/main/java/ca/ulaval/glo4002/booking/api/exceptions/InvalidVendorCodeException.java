package ca.ulaval.glo4002.booking.api.exceptions;

public class InvalidVendorCodeException extends ClientError {

    public InvalidVendorCodeException() {
        super("INVALID_VENDOR_CODE", "invalid vendor code");
    }
}
