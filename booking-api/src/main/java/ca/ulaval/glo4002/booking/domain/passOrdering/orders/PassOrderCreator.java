package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import java.time.OffsetDateTime;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.interfaces.dtos.PassDTO;
import ca.ulaval.glo4002.booking.persistance.heap.exceptions.RecordAlreadyExistsException;

public class PassOrderCreator {

    private PassOrderPersistance passOrderPersistance;
    private PassPersistance passPersistance;
    private PassOrderFactory passOrderFactory;

    public PassOrderCreator(Repository repository, OffsetDateTime festivalStart, OffsetDateTime festivalEnd) {
        this.passOrderPersistance = repository.getPassOrderPersistance();
        this.passPersistance = repository.getPassPersistance();
        this.passOrderFactory = new PassOrderFactory(festivalStart, festivalEnd);
    }

    public PassOrder orderPasses(OffsetDateTime orderDate, String vendorCode, List<PassDTO> passDTOs) throws RecordAlreadyExistsException {
        PassOrder passOrder = this.passOrderFactory.create(orderDate, vendorCode, passDTOs);
        saveObjects(passOrder);
        return passOrder;
    }

    private void saveObjects(PassOrder passOrder) throws RecordAlreadyExistsException {
        for (Pass pass : passOrder.getPasses()) {
            this.passPersistance.save(pass);
        }

        this.passOrderPersistance.save(passOrder);
    }
}
