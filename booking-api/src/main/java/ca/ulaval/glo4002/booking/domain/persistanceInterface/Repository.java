package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;

public interface Repository {

    public PassOrderPersistance getPassOrderPersistance();
    public PassPersistance getPassPersistance();
    public ShuttlePersistance getShuttlePersistance();
    public OxygenHistoryRepository getOxygenHistoryRepository();
    public OxygenInventoryRepository getOxygenInventoryRepository();
}
