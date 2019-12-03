package ca.ulaval.glo4002.booking.domain.orders;

import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumber;

public interface PassOrderRepository {
    PassOrder findByOrderNumber(OrderNumber orderNumber);
    void add(PassOrder order);
}
