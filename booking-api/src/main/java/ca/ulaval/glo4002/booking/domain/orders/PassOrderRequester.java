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
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;

public class PassOrderRequester implements PassOrderExposer {

    private PassOrderRepository passOrderPersistance;
    private PassRepository passRepository;
    private PassOrderFactory passOrderFactory;

    public PassOrderRequester(Repository repository, Glow4002 festival) {
        passOrderPersistance = repository.getPassOrderRepository();
        passRepository = repository.getPassRepository();
        passOrderFactory = new PassOrderFactory(festival);
    }

    @Override
    public Optional<PassOrder> getOrder(Long id) {
        return passOrderPersistance.getById(id);
    }

    public PassOrder orderPasses(OffsetDateTime orderDate, String vendorCode, PassRequest passRequest) throws OutOfSaleDatesException, OutOfFestivalDatesException {
        PassOrder passOrder = passOrderFactory.create(orderDate, vendorCode, passRequest);
        saveObjects(passOrder);
        return passOrder;
    }

    private void saveObjects(PassOrder passOrder) {
        for (Pass pass : passOrder.getPasses()) {
            passRepository.save(pass);
        }
        passOrderPersistance.save(passOrder);
    }
}
