package ca.ulaval.glo4002.booking.application.orders;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDto;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDtoMapper;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumber;

public class PassOrderFetchingUseCase {

    @Inject private PassOrderRepository passOrderRepository;
    @Inject private PassOrderDtoMapper passOrderDtoMapper;

    public PassOrderDto getOrder(OrderNumber orderNumber) {
        return passOrderDtoMapper.toDto(passOrderRepository.findByOrderNumber(orderNumber));
    }
}
