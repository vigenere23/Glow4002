package ca.ulaval.glo4002.booking.interfaces.rest.orders.dtos;

import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;

public class OrderResponse {

    public final float orderPrice;
    public final List<PassResponse> passes;

    public OrderResponse(Money orderPrice, List<Pass> passes) {
        this.orderPrice = orderPrice.rounded(2, RoundingMode.HALF_UP).getAmount().floatValue();
        this.passes = passes.stream().map(Pass::serialize).collect(Collectors.toList());
    }
}
