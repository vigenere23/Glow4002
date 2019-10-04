package ca.ulaval.glo4002.booking.persistance.heap;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.ShuttlePersistance;

public class HeapRepository implements Repository {
    
    private final OxygenPersistance oxygenPersistance;
    private final PassOrderPersistance passOrderPersistance;
    private final PassPersistance passPersistance;
    private final ShuttlePersistance shuttlePersistance;
    
    public HeapRepository() {
        oxygenPersistance = new HeapOxygenPersistance();
        passOrderPersistance = new HeapPassOrderPersistance();
        passPersistance = new HeapPassPersistance();
        shuttlePersistance = new HeapShuttlePersistance();
    }

    @Override
    public OxygenPersistance getOxygenPersistance() {
        return oxygenPersistance;
    }

    @Override
    public PassOrderPersistance getPassOrderPersistance() {
        return passOrderPersistance;
    }

    @Override
    public PassPersistance getPassPersistance() {
        return passPersistance;
    }

    @Override
    public ShuttlePersistance getShuttlePersistance() {
        return shuttlePersistance;
    }
}
