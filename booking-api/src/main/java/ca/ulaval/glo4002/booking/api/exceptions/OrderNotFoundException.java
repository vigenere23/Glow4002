package ca.ulaval.glo4002.booking.api.exceptions;

public class OrderNotFoundException extends ClientError {

    public OrderNotFoundException(long id) {
        super(404, ClientErrorType.ORDER_NOT_FOUND, String.format("order with number %d not found", id));
    }
}
