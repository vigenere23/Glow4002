package ca.ulaval.glo4002.booking.api.exceptions;

import ca.ulaval.glo4002.booking.domain.orders.OrderNumber;

public class OrderNotFoundException extends ClientError {

    public OrderNotFoundException(OrderNumber orderNumber) {
        super(404, "ORDER_NOT_FOUND", String.format("order with number %s not found", orderNumber.getValue()));
    }
}
