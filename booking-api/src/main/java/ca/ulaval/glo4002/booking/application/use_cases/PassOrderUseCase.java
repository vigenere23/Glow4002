package ca.ulaval.glo4002.booking.application.use_cases;

import java.time.OffsetDateTime;
import java.util.Optional;

import ca.ulaval.glo4002.booking.api.resources.passOrder.PassRequest;
import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class PassOrderUseCase {

    private PassOrderFactory passOrderFactory;
    private PassOrderRepository passOrderRepository;
    private PassRepository passRepository;
    private TransportReserver transportReserver;
    private OxygenRequester oxygenRequester;
    private IncomeSaver incomeSaver;

    public PassOrderUseCase(PassOrderFactory passFactory, PassOrderRepository passOrderRepository,
            TransportReserver transportReserver, OxygenRequester oxygenRequester, PassRepository passRepository,
            IncomeSaver incomeSaver) {
        this.passOrderFactory = passFactory;
        this.passOrderRepository = passOrderRepository;
        this.transportReserver = transportReserver;
        this.oxygenRequester = oxygenRequester;
        this.passRepository = passRepository;
        this.incomeSaver = incomeSaver;
    }

    public Optional<PassOrder> getOrder(OrderNumber orderNumber) {
        return passOrderRepository.findByOrderNumber(orderNumber);
    }

    public PassOrder orchestPassCreation(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest) {
        PassOrder passOrder = passOrderFactory.create(
            orderDate, vendorCode, passRequest.getPassOption(), passRequest.getPassCategory(), passRequest.getEventDates()
        );
        passOrder.saveIncome(incomeSaver);
        passOrderRepository.save(passOrder);
        for (Pass pass : passOrder.getPasses()) {
            passRepository.save(pass);
            pass.reserveShuttles(transportReserver);
            pass.reserveOxygen(orderDate, oxygenRequester);
        }
        return passOrder;
    }
}
