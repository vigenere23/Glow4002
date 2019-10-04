package ca.ulaval.glo4002.booking.interfaces.rest.dtoMappers;


import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.orders.PassOrderResponse;

public class PassOrderResponseMapper {

    public PassOrderResponse getPassOrderResponse(PassOrder passOrder) {
        return new PassOrderResponse(passOrder.getPrice(), passOrder.getPasses());
    }
}
