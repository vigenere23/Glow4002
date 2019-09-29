package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordAlreadyExistsException;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordNotFoundException;

public interface PassPersistance {
    public Pass getById(Long id) throws RecordNotFoundException;
    public void save(Pass pass) throws RecordAlreadyExistsException;
    public void remove(Long id) throws RecordNotFoundException;
}
