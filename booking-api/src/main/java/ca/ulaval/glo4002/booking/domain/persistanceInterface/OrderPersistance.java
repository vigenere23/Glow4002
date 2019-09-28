package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.Order;

public interface OrderPersistance {
	public Order getById(Long id);
	public void save(Order order);
}
