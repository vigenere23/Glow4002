package ca.ulaval.glo4002.booking.domain.orders;

import java.time.OffsetDateTime;
import java.util.Optional;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;

public class PassOrderRequester implements PassOrderExposer {

    private PassOrderRepository passOrderRepository;
    private PassOrderFactory passOrderFactory;

    public PassOrderRequester(PassOrderRepository passOrderRepository, FestivalDates festivalDates) {
        this.passOrderRepository = passOrderRepository;
        passOrderFactory = new PassOrderFactory(festivalDates);
    }

    @Override
    public Optional<PassOrder> getOrder(OrderNumber orderNumber) {
        return passOrderRepository.findByOrderNumber(orderNumber);
    }

    public PassOrder orderPasses(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest) {
        PassOrder passOrder = passOrderFactory.create(orderDate, vendorCode, passRequest);
        passOrderRepository.save(passOrder);
        return passOrder;
    }
}
