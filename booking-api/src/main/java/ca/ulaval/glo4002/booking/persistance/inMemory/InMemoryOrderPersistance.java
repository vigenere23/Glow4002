package ca.ulaval.glo4002.booking.persistance.inMemory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.Order;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OrderPersistance;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordAlreadyExistsException;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordNotFoundException;

public class InMemoryOrderPersistance implements OrderPersistance {

	private static final AtomicLong idGenerator = new AtomicLong(0);
	private Map<Long, Order> orders;

	public InMemoryOrderPersistance() {
		this.orders = new HashMap<>();
	}

	@Override
	public Order getById(Long id) throws RecordNotFoundException {
		Order order = this.orders.get(id);
		
		if (order == null) {
			throw new RecordNotFoundException();
		}

		return order;
	}

	@Override
	public void save(Order order) throws RecordAlreadyExistsException {
		if (this.orders.containsValue(order)) {
			throw new RecordAlreadyExistsException();
		}

		order.setId(idGenerator.getAndIncrement());
		this.orders.put(order.getId(), order);
	}

	@Override
	public void remove(Long id) throws RecordNotFoundException {
		if (!this.orders.containsKey(id)) {
			throw new RecordNotFoundException();
		}

		this.orders.remove(id);
	}
}
