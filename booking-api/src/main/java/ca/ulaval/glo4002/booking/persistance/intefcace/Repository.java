package ca.ulaval.glo4002.booking.persistance.intefcace;

public interface Repository {
	OxygenPersistance getOxygenPersistance();
	PassPersistance getPassPersistance();
	ShuttlePersistance getShuttlePersistance();
}
