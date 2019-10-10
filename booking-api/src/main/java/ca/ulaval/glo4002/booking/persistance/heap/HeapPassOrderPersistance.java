package ca.ulaval.glo4002.booking.persistance.heap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.orders.ID;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;

public class HeapPassOrderPersistance implements PassOrderPersistance {

    private static final AtomicLong idGenerator = new AtomicLong(0);
    private Map<ID, PassOrder> passOrders;

    public HeapPassOrderPersistance() {
        passOrders = new HashMap<>();
    }

    @Override
    public Optional<PassOrder> getById(Long id) {
        List<ID> idWanted = passOrders.keySet().stream().filter(key -> key.getId().equals(id)).collect(Collectors.toList());   
        Optional<PassOrder> passOrder = idWanted.isEmpty() ?  Optional.ofNullable(null) : Optional.ofNullable(passOrders.get(idWanted.get(0)));
        return passOrder;
    }

    @Override
    public void save(PassOrder passOrder) {
        if (!passOrders.containsValue(passOrder) && passOrder != null) {
            passOrder.setId(new ID(idGenerator.getAndIncrement()));
            passOrders.put(passOrder.getOrderNumber(), passOrder);
        }
    }
}
