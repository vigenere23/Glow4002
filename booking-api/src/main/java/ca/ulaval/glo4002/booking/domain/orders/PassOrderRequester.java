package ca.ulaval.glo4002.booking.domain.orders;

import java.time.OffsetDateTime;
import java.util.Optional;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;

public class PassOrderRequester implements PassOrderExposer {

    private PassOrderRepository passOrderRepository;
    private PassOrderFactory passOrderFactory;

    public PassOrderRequester(PassOrderRepository passOrderRepository, Glow4002 festival) {
        this.passOrderRepository = passOrderRepository;
        passOrderFactory = new PassOrderFactory(festival);
    }

    @Override
    public Optional<PassOrder> getOrder(OrderNumber orderNumber) {
        return passOrderRepository.findByOrderNumber(orderNumber);
    }

    public PassOrder orderPasses(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest) throws OutOfSaleDatesException, OutOfFestivalDatesException {
        PassOrder passOrder = passOrderFactory.create(orderDate, vendorCode, passRequest);
        passOrderRepository.save(passOrder);
        return passOrder;
    }
}
