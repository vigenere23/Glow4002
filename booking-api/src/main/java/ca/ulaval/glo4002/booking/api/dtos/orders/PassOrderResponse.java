package ca.ulaval.glo4002.booking.api.dtos.orders;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.api.dtoMappers.PassResponseMapper;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.Pass;

public class PassOrderResponse {

    public final double orderPrice;
    public final List<PassResponse> passes;

    public PassOrderResponse(Price orderPrice, List<Pass> passes) {
        PassResponseMapper passResponseMapper = new PassResponseMapper();
        this.orderPrice = orderPrice.getRoundedAmount(2);
        this.passes = passes.stream()
            .map(pass -> passResponseMapper.getPassResponse(pass))
            .collect(Collectors.toList());
    }
}
