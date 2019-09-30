package ca.ulaval.glo4002.booking.persistance.heap;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenPersistance;;

public class HeapOxygenPersistance implements OxygenPersistance {

	private OxygenInventory oxygenInventory;
	private OxygenHistory oxygenHistory;

	public HeapOxygenPersistance() {
		oxygenInventory = new InMemoryOxygenInventory();
		oxygenHistory = new InMemoryOxygenHistory();
	}

	@Override
	public OxygenInventory getOxygenInventory() {
		return oxygenInventory;
	}

	@Override
	public OxygenHistory getOxygenHistory() {
		return oxygenHistory;
	}
}
