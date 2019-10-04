package ca.ulaval.glo4002.booking.persistance.heap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;

public class HeapPassOrderPersistance implements PassOrderPersistance {

	private static final AtomicLong idGenerator = new AtomicLong(0);
	private Map<Long, PassOrder> passOrders;

	public HeapPassOrderPersistance() {
		this.passOrders = new HashMap<>();
	}

	@Override
	public Optional<PassOrder> getById(Long id) {
		PassOrder passOrder = this.passOrders.get(id);
		return Optional.ofNullable(passOrder);
	}

	@Override
	public void save(PassOrder passOrder) {
		if (!this.passOrders.containsValue(passOrder)) {
			passOrder.setId(idGenerator.getAndIncrement());
			this.passOrders.put(passOrder.getId(), passOrder);
		}
	}
}
