package ca.ulaval.glo4002.booking.application.orders.dtos;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.orders.PassOrder;

public class PassOrderDtoMapper {

    private PassDtoMapper passDtoMapper;

    public PassOrderDtoMapper(PassDtoMapper passDtoMapper) {
        this.passDtoMapper = passDtoMapper;
    }

    public PassOrderDto toDto(PassOrder passOrder) {
        List<PassDto> passes = passDtoMapper.toDtos(passOrder.getPasses());
        return new PassOrderDto(passOrder.getOrderNumber(), passOrder.getPrice(), passes);
    }
}
