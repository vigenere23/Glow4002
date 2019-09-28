package ca.ulaval.glo4002.booking.persistance.inMemory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;

public class InMemoryPassPersistance implements PassPersistance {

    private static final AtomicLong idGenerator = new AtomicLong(0);
	private Map<Long, Pass> passes;

	public InMemoryPassPersistance() {
		this.passes = new HashMap<>();
	}

	@Override
	public Pass getById(Long id) {
		return this.passes.get(id);
	}

	@Override
	public void save(Pass pass) {
		pass.setId(idGenerator.getAndIncrement());
		this.passes.put(pass.getId(), pass);
	}
}
