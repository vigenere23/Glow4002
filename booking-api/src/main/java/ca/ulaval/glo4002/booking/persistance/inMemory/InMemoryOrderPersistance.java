package ca.ulaval.glo4002.booking.persistance.inMemory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.Order;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OrderPersistance;

public class InMemoryOrderPersistance implements OrderPersistance {

	private static final AtomicLong idGenerator = new AtomicLong(0);
	private Map<Long, Order> orders;

	public InMemoryOrderPersistance() {
		this.orders = new HashMap<>();
	}

	@Override
	public Order getById(Long id) {
		return this.orders.get(id);
	}

	@Override
	public void save(Order order) {
		order.setId(idGenerator.getAndIncrement());
		this.orders.put(order.getId(), order);
	}
}
