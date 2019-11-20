package ca.ulaval.glo4002.booking.api.resources.orders.dto;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.Pass;

public class PassOrderResponse {

    public final float orderPrice;
    public final List<PassDto> passes;

    public PassOrderResponse(Price orderPrice, List<Pass> passes) {
        PassMapper passResponseMapper = new PassMapper();
        this.orderPrice = orderPrice.getRoundedAmount(2);
        this.passes = passes.stream()
            .map(pass -> passResponseMapper.toDto(pass))
            .collect(Collectors.toList());
    }
}
