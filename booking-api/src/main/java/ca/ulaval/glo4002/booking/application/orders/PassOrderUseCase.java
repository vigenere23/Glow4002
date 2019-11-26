package ca.ulaval.glo4002.booking.application.orders;

import java.time.OffsetDateTime;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.requests.PassRequest;
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

    @Inject private PassOrderFactory passOrderFactory;
    @Inject private PassOrderRepository passOrderRepository;
    @Inject private PassRepository passRepository;
    @Inject private TransportReserver transportReserver;
    @Inject private OxygenRequester oxygenRequester;
    @Inject private IncomeSaver incomeSaver;
    @Inject private PassOrderDtoMapper passOrderDtoMapper;

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
