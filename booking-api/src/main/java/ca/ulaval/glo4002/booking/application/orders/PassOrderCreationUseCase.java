package ca.ulaval.glo4002.booking.application.orders;

import java.time.OffsetDateTime;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDto;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDtoMapper;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.VendorCode;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.requests.PassRequest;

public class PassOrderCreationUseCase {

    @Inject private PassOrderFactory passOrderFactory;
    @Inject private PassOrderRepository passOrderRepository;
    @Inject private PassRepository passRepository;
    @Inject private TransportReserver transportReserver;
    @Inject private OxygenRequester oxygenRequester;
    @Inject private IncomeSaver incomeSaver;
    @Inject private PassOrderDtoMapper passOrderDtoMapper;

    public PassOrderDto orderPasses(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest) {
        PassOrder passOrder = passOrderFactory.create(
            orderDate, vendorCode, passRequest.passOption, passRequest.passCategory, passRequest.eventDates
        );

        for (Pass pass : passOrder.getPasses()) {
            pass.reserveShuttles(transportReserver);
            pass.reserveOxygen(orderDate, oxygenRequester);
            passRepository.add(pass);
        }
        
        passOrder.saveIncome(incomeSaver);
        passOrderRepository.add(passOrder);

        return passOrderDtoMapper.toDto(passOrder);
    }
}
