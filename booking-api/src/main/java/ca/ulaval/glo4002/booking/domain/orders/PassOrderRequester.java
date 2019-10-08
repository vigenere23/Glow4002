package ca.ulaval.glo4002.booking.domain.orders;

import java.time.OffsetDateTime;
import java.util.Optional;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.services.exposers.PassOrderExposer;

public class PassOrderRequester implements PassOrderExposer {

    private PassOrderPersistance passOrderPersistance;
    private PassPersistance passPersistance;
    private PassOrderFactory passOrderFactory;

    public PassOrderRequester(Repository repository, Glow4002 festival) {
        passOrderPersistance = repository.getPassOrderPersistance();
        passPersistance = repository.getPassPersistance();
        passOrderFactory = new PassOrderFactory(festival);
    }

    @Override
    public Optional<PassOrder> getOrder(Long id) {
        return passOrderPersistance.getById(id);
    }

    @Override
    public PassOrder orderPasses(OffsetDateTime orderDate, String vendorCode, PassRequest passRequest) throws OutOfSaleDatesException, OutOfFestivalDatesException {
        PassOrder passOrder = passOrderFactory.create(orderDate, vendorCode, passRequest);
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
