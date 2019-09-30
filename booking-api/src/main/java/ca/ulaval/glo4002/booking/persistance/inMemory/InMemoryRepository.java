package ca.ulaval.glo4002.booking.persistance.inMemory;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.ShuttlePersistance;

public class InMemoryRepository implements Repository{
	
	private final OxygenPersistance oxygenPersistance;
	private final PassOrderPersistance passOrderPersistance;
	private final PassPersistance passPersistance;
	private final ShuttlePersistance shuttlePersistance;
	
	public InMemoryRepository() {
		this.oxygenPersistance = new InMemoryOxygenPersistance();
		this.passOrderPersistance = new InMemoryPassOrderPersistance();
		this.passPersistance = new InMemoryPassPersistance();
		this.shuttlePersistance = new InMemoryShuttlePersistance();
	}

	@Override
	public OxygenPersistance getOxygenPersistance() {
		return oxygenPersistance;
	}

	@Override
	public PassOrderPersistance getPassOrderPersistance() {
		return passOrderPersistance;
	}

	@Override
	public PassPersistance getPassPersistance() {
		return passPersistance;
	}

	@Override
	public ShuttlePersistance getShuttlePersistance() {
		return shuttlePersistance;
	}
}
