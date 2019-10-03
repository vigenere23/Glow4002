package ca.ulaval.glo4002.booking.interfaces.exceptions;

public class OrderNotFound extends ClientError {

    private static final long serialVersionUID = -6517697008635280695L;

    public OrderNotFound(long id) {
        super(404, ClientErrorType.ORDER_NOT_FOUND, String.format("oder with number %d not found", id));
    }
}
