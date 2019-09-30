package ca.ulaval.glo4002.booking.persistance.inMemory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordAlreadyExistsException;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordNotFoundException;

public class InMemoryPassOrderPersistance implements PassOrderPersistance {

	private static final AtomicLong idGenerator = new AtomicLong(0);
	private Map<Long, PassOrder> passOrders;

	public InMemoryPassOrderPersistance() {
		this.passOrders = new HashMap<>();
	}

	@Override
	public PassOrder getById(Long id) throws RecordNotFoundException {
		PassOrder passOrder = this.passOrders.get(id);
		
		if (passOrder == null) {
			throw new RecordNotFoundException();
		}

		return passOrder;
	}

	@Override
	public void save(PassOrder passOrder) throws RecordAlreadyExistsException {
		if (this.passOrders.containsValue(passOrder)) {
			throw new RecordAlreadyExistsException();
		}

		passOrder.setId(idGenerator.getAndIncrement());
		this.passOrders.put(passOrder.getId(), passOrder);
	}

	@Override
	public void remove(Long id) throws RecordNotFoundException {
		if (!this.passOrders.containsKey(id)) {
			throw new RecordNotFoundException();
		}

		this.passOrders.remove(id);
	}
}
