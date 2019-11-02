package ca.ulaval.glo4002.booking.api.dtos.orders;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.domain.orders.VendorCode;

public class PassOrderRequest {

    public final OffsetDateTime orderDate;
    public final VendorCode vendorCode;
    public final PassRequest passes;

    @JsonCreator
    public PassOrderRequest(
        @JsonProperty(value = "orderDate", required = true) String orderDate,
        @JsonProperty(value = "vendorCode", required = true) String vendorCode,
        @JsonProperty(value = "passes", required = true) PassRequest passes
    ) {
        this.orderDate = OffsetDateTime.parse(orderDate);
        this.vendorCode = VendorCode.valueOf(vendorCode);
        this.passes = passes;
    }
}
