package ca.ulaval.glo4002.booking.persistance.heap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;

public class HeapPassPersistance implements PassPersistance {

    private static final AtomicLong idGenerator = new AtomicLong(0);
	private Map<Long, Pass> passes;

	public HeapPassPersistance() {
		this.passes = new HashMap<>();
	}

	@Override
	public Optional<Pass> getById(Long id) {
		Pass pass = this.passes.get(id);

		return Optional.ofNullable(pass);
	}

	@Override
	public void save(Pass pass) {
		if (!this.passes.containsValue(pass)) {			
			pass.setId(idGenerator.getAndIncrement());
			this.passes.put(pass.getId(), pass);
		}
	}
}
