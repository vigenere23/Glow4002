package ca.ulaval.glo4002.booking.domain.persistanceInterface;

public interface Repository {

    public OxygenPersistance getOxygenPersistance();
    public PassOrderPersistance getPassOrderPersistance();
    public PassPersistance getPassPersistance();
    public ShuttlePersistance getShuttlePersistance();
}
