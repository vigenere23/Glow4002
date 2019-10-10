package ca.ulaval.glo4002.booking.persistance.heap;

import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;

public class HeapRepository implements Repository {
    
    private final PassOrderRepository passOrderRepository;
    private final PassRepository passRepository;
    private final ShuttleRepository shuttleRepository;
    private final OxygenHistoryRepository oxygenHistoryRepository;
    private final OxygenInventoryRepository oxygenInventoryRepository;
    
    public HeapRepository() {
        passOrderRepository = new HeapPassOrderPersistance();
        passRepository = new HeapPassRepository();
        shuttleRepository = new HeapShuttleRepository();
        oxygenHistoryRepository = new HeapOxygenHistory();
        oxygenInventoryRepository = new HeapOxygenInventory();
    }

    @Override
    public PassOrderRepository getPassOrderRepository() {
        return passOrderRepository;
    }

    @Override
    public PassRepository getPassRepository() {
        return passRepository;
    }

    @Override
    public ShuttleRepository getShuttleRepository() {
        return shuttleRepository;
    }

    @Override
    public OxygenHistoryRepository getOxygenHistoryRepository() {
        return oxygenHistoryRepository;
    }

    @Override
    public OxygenInventoryRepository getOxygenInventoryRepository() {
        return oxygenInventoryRepository;
    }
}
