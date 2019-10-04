package ca.ulaval.glo4002.booking.interfaces.rest.dtos.orders;

import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.interfaces.rest.dtoMappers.PassResponseMapper;

public class PassOrderResponse {

    public final float orderPrice;
    public final List<PassResponse> passes;

    public PassOrderResponse(Money orderPrice, List<Pass> passes) {
        this.orderPrice = orderPrice.rounded(2, RoundingMode.HALF_UP).getAmount().floatValue();
        this.passes = passes.stream()
            .map(pass -> new PassResponseMapper().getPassResponse(pass))
            .collect(Collectors.toList());
    }
}
