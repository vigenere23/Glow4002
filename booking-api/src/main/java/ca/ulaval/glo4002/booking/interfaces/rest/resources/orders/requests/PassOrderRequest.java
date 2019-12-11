package ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.requests;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.domain.orders.VendorCode;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatClientException;

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
        try {
            this.orderDate = OffsetDateTime.parse(orderDate);
            this.vendorCode = VendorCode.valueOf(vendorCode);
            this.passes = passes;
        }
        catch (Exception exception) {
            throw new InvalidFormatClientException();
        }
    }
}
