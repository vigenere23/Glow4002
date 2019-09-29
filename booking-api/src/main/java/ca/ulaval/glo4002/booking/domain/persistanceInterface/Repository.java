package ca.ulaval.glo4002.booking.domain.persistanceInterface;

public interface Repository {
	OxygenPersistance getOxygenPersistance();
	OrderPersistance getOrderPersistance();
	PassPersistance getPassPersistance();
	ShuttlePersistance getShuttlePersistance();
}
