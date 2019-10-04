package ca.ulaval.glo4002.booking.interfaces.rest.exceptions;

public class InvalidVendorCodeException extends ClientError {

    public InvalidVendorCodeException() {
        super(ClientErrorType.INVALID_VENDOR_CODE, "invalid vendor code");
    }
}
