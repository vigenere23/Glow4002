package ca.ulaval.glo4002.booking.application;

import java.time.OffsetDateTime;
import java.util.Optional;

import ca.ulaval.glo4002.booking.api.resources.passOrder.PassRequest;
import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class PassOrderUseCase {

    private PassOrderFactory passOrderFactory;
    private PassOrderRepository passOrderRepository;
    private TransportReserver transportReserver;
    private OxygenReserver oxygenReserver;

    public PassOrderUseCase(PassOrderFactory passFactory, PassOrderRepository passOrderRepository, TransportReserver transportReserver, OxygenReserver oxygenReserver) {
        this.passOrderFactory = passFactory;
        this.passOrderRepository = passOrderRepository;
        this.transportReserver = transportReserver;
        this.oxygenReserver = oxygenReserver;
    }

    public Optional<PassOrder> getOrder(OrderNumber orderNumber) {
        return passOrderRepository.findByOrderNumber(orderNumber);
    }

    public PassOrder orchestPassCreation(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest) {
        PassOrder passOrder = passOrderFactory.create(orderDate, vendorCode, passRequest.getPassOption(), passRequest.getPassCategory(), passRequest.getEventDates());
        reservePassUtilities(orderDate, passOrder);
        passOrderRepository.save(passOrder);

        return passOrder;
    }

    private void reservePassUtilities(OffsetDateTime orderDate, PassOrder passOrder) {
        for (Pass pass : passOrder.getPasses()) {
            pass.reserveShuttles(transportReserver);
            pass.reserveOxygen(orderDate.toLocalDate(), oxygenReserver);
        }
    }
}
