package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.booking.domain.exceptions.ItemNotFound;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumber;

public class InMemoryPassOrderRepository implements PassOrderRepository {

    private Map<String, PassOrder> passOrders;

    public InMemoryPassOrderRepository() {
        passOrders = new HashMap<>();
    }

    @Override
    public PassOrder findByOrderNumber(OrderNumber orderNumber) {
        PassOrder passOrder = passOrders.get(orderNumber.getValue());
        if (passOrder == null) {
            throw new ItemNotFound("order", orderNumber.getValue());
        }
        return passOrder;
    }

    @Override
    public void add(PassOrder passOrder) {
        if (!passOrders.containsValue(passOrder) && passOrder != null) {
            passOrders.put(passOrder.getOrderNumber().getValue(), passOrder);
        }
    }
}
