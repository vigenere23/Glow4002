package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;
import ca.ulaval.glo4002.booking.infrastructure.persistance.exceptions.NotFoundException;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;

public class HeapPassOrderRepository implements PassOrderRepository {

    private Map<String, PassOrder> passOrders;

    public HeapPassOrderRepository() {
        passOrders = new HashMap<>();
    }

    @Override
    public PassOrder findByOrderNumber(OrderNumber orderNumber) {
        PassOrder passOrder = passOrders.get(orderNumber.getValue());
        if (passOrder == null) {
            throw new NotFoundException("order", orderNumber.getValue());
        }
        return passOrder;
    }

    @Override
    public void save(PassOrder passOrder) {
        if (!passOrders.containsValue(passOrder) && passOrder != null) {
            passOrders.put(passOrder.getOrderNumber().getValue(), passOrder);
        }
    }
}
