package ca.ulaval.glo4002.booking.domain.persistanceInteface;

public interface Repository {
	OxygenPersistance getOxygenPersistance();
	PassPersistance getPassPersistance();
	ShuttlePersistance getShuttlePersistance();
}
