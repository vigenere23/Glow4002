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

public class PassOrderRequester implements PassOrderExposer {

    private PassOrderRepository passOrderRepository;
    private PassRepository passRepository;
    private PassOrderFactory passOrderFactory;

    public PassOrderRequester(PassOrderRepository passOrderRepository, PassRepository passRepository, Glow4002 festival) {
        this.passOrderRepository = passOrderRepository;
        this.passRepository = passRepository;
        passOrderFactory = new PassOrderFactory(festival);
    }

    @Override
    public Optional<PassOrder> getOrder(Long id) {
        return passOrderRepository.findById(id);
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
        passOrderRepository.save(passOrder);
    }
}
