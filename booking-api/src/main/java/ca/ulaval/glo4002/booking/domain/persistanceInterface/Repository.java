package ca.ulaval.glo4002.booking.domain.persistanceInterface;

public interface Repository {
	OxygenPersistance getOxygenPersistance();
	PassOrderPersistance getPassOrderPersistance();
	PassPersistance getPassPersistance();
	ShuttlePersistance getShuttlePersistance();
}
