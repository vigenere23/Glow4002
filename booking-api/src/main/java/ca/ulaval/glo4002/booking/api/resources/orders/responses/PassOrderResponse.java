package ca.ulaval.glo4002.booking.api.resources.orders.responses;

import java.util.List;

import ca.ulaval.glo4002.booking.application.orders.dtos.PassDto;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDto;

public class PassOrderResponse {

    public final float orderPrice;
    public final List<PassDto> passes;

    public PassOrderResponse(PassOrderDto passOrderDto) {
        this.orderPrice = passOrderDto.orderPrice;
        this.passes = passOrderDto.passes;
    }
}
