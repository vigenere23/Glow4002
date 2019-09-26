package ca.ulaval.glo4002.booking.domain.persistanceInterface;

public interface Repository {
	OxygenPersistance getOxygenPersistance();
	PassPersistance getPassPersistance();
	ShuttlePersistance getShuttlePersistance();
}
