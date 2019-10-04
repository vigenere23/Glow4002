package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passOrdering.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.orders.PassRequest;

public class PassOrderService {

    private PassOrderPersistance passOrderPersistance;
    private PassPersistance passPersistance;
    private PassOrderFactory passOrderFactory;

    public PassOrderService(Repository repository, Glow4002 festival) {
        passOrderPersistance = repository.getPassOrderPersistance();
        passPersistance = repository.getPassPersistance();
        passOrderFactory = new PassOrderFactory(festival);
    }

    public Optional<PassOrder> getOrder(long id) {
        return passOrderPersistance.getById(id);
    }

    public PassOrder orderPasses(OffsetDateTime orderDate, String vendorCode, List<PassRequest> passRequests) throws OutOfSaleDatesException, OutOfFestivalDatesException {
        PassOrder passOrder = passOrderFactory.create(orderDate, vendorCode, passRequests);
        saveObjects(passOrder);
        return passOrder;
    }

    private void saveObjects(PassOrder passOrder) {
        for (Pass pass : passOrder.getPasses()) {
            passPersistance.save(pass);
        }
        passOrderPersistance.save(passOrder);
    }
}
