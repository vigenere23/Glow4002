package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.Order;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordAlreadyExistsException;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordNotFoundException;

public interface OrderPersistance {
	public Order getById(Long id) throws RecordNotFoundException;
	public void save(Order order) throws RecordAlreadyExistsException;
	public void remove(Long id) throws RecordNotFoundException;
}
