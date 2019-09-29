package ca.ulaval.glo4002.booking.persistance.inMemory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordAlreadyExistsException;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordNotFoundException;

public class InMemoryPassPersistance implements PassPersistance {

    private static final AtomicLong idGenerator = new AtomicLong(0);
	private Map<Long, Pass> passes;

	public InMemoryPassPersistance() {
		this.passes = new HashMap<>();
	}

	@Override
	public Pass getById(Long id) throws RecordNotFoundException {
		Pass pass = this.passes.get(id);
		
		if (pass == null) {
			throw new RecordNotFoundException();
		}

		return pass;
	}

	@Override
	public void save(Pass pass) throws RecordAlreadyExistsException {
		if (this.passes.containsValue(pass)) {
			throw new RecordAlreadyExistsException();
		}

		pass.setId(idGenerator.getAndIncrement());
		this.passes.put(pass.getId(), pass);
	}

	@Override
	public void remove(Long id) throws RecordNotFoundException {
		if (!this.passes.containsKey(id)) {
			throw new RecordNotFoundException();
		}

		this.passes.remove(id);
	}
}