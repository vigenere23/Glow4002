package ca.ulaval.glo4002.booking.api.dtoMappers;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassOrderResponse;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;

public class PassOrderResponseMapper {

    public PassOrderResponse getPassOrderResponse(PassOrder passOrder) {
        return new PassOrderResponse(passOrder.getPrice(), passOrder.getPasses());
    }
}
