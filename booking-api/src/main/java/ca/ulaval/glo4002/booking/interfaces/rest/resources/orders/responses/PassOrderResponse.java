package ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.responses;

import java.util.List;

import ca.ulaval.glo4002.booking.application.orders.dtos.PassDto;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDto;

public class PassOrderResponse {

    public final float orderPrice;
    public final List<PassDto> passes;

    public PassOrderResponse(PassOrderDto passOrderDto) {
        this.orderPrice = passOrderDto.price;
        this.passes = passOrderDto.passes;
    }
}
