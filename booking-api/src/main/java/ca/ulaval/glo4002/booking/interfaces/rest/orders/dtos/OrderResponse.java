package ca.ulaval.glo4002.booking.interfaces.rest.orders.dtos;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;

public class OrderResponse {

    public final String orderDate;
    public final String vendorCode;
    public final List<PassResponse> passes;

    public OrderResponse(OffsetDateTime orderDate, String vendorCode, List<Pass> passes) {
        this.orderDate = orderDate.toString();
        this.vendorCode = vendorCode.toString();
        this.passes = passes.stream().map(Pass::serialize).collect(Collectors.toList());
    }
}
