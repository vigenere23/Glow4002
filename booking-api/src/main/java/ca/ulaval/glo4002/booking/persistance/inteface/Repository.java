package ca.ulaval.glo4002.booking.persistance.inteface;

public interface Repository {
	OxygenPersistance getOxygenPersistance();
	PassPersistance getPassPersistance();
	ShuttlePersistance getShuttlePersistance();
}
