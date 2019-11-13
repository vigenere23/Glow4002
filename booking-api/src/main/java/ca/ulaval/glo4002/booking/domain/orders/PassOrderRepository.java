package ca.ulaval.glo4002.booking.domain.orders;

import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;

public interface PassOrderRepository {
    
    public Optional<PassOrder> findByOrderNumber(OrderNumber orderNumber);
    public void save(PassOrder order);
}
