package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;

public interface Repository {

    public PassOrderRepository getPassOrderRepository();
    public PassPersistance getPassPersistance();
    public ShuttleRepository getShuttleRepository();
    public OxygenHistoryRepository getOxygenHistoryRepository();
    public OxygenInventoryRepository getOxygenInventoryRepository();
}
