package ca.ulaval.glo4002.booking.application.orders;

import java.time.OffsetDateTime;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.api.resources.orders.requests.PassRequest;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDto;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDtoMapper;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.VendorCode;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumber;
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
    private PassOrderDtoMapper passOrderDtoMapper;

    @Inject
    public PassOrderUseCase(
        PassOrderFactory passFactory,
        PassOrderRepository passOrderRepository,
        TransportReserver transportReserver,
        OxygenRequester oxygenRequester,
        PassRepository passRepository,
        IncomeSaver incomeSaver,
        PassOrderDtoMapper passOrderDtoMapper
    ) {
        this.passOrderFactory = passFactory;
        this.passOrderRepository = passOrderRepository;
        this.transportReserver = transportReserver;
        this.oxygenRequester = oxygenRequester;
        this.passRepository = passRepository;
        this.incomeSaver = incomeSaver;
        this.passOrderDtoMapper = passOrderDtoMapper;
    }

    public PassOrderDto getOrder(OrderNumber orderNumber) {
        return passOrderDtoMapper.toDto(passOrderRepository.findByOrderNumber(orderNumber));
    }

    public PassOrderDto orchestPassCreation(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest) {
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
        return passOrderDtoMapper.toDto(passOrder);
    }
}
