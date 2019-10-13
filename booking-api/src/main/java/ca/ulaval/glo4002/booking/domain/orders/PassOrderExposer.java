package ca.ulaval.glo4002.booking.domain.orders;

import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.orders.PassOrder;

public interface PassOrderExposer {

    public Optional<PassOrder> getOrder(OrderNumber orderNumber);
}
