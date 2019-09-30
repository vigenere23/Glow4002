package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordAlreadyExistsException;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordNotFoundException;

public interface PassOrderPersistance {
	public PassOrder getById(Long id) throws RecordNotFoundException;
	public void save(PassOrder order) throws RecordAlreadyExistsException;
	public void remove(Long id) throws RecordNotFoundException;
}
