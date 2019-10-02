package ca.ulaval.glo4002.booking.interfaces.rest.orders;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.interfaces.rest.orders.orderDTOs.PassDTO;

public class OrderResponse {

    public final String orderDate;
    public final String vendorCode;
    public final PassDTO passes;

    public OrderResponse(OffsetDateTime orderDate, String vendorCode, PassDTO passes) {
        this.orderDate = orderDate.toString();
        this.vendorCode = vendorCode.toString();
        this.passes = passes;
    }
}
