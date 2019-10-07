package ca.ulaval.glo4002.booking.api.dtos.orders;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;

public class PassOrderRequest {

    public final OffsetDateTime orderDate;
    public final String vendorCode;
    public final PassRequest passes;

    @JsonCreator
    public PassOrderRequest(
        @JsonProperty(value = "orderDate", required = true) String orderDate,
        @JsonProperty(value = "vendorCode", required = true) String vendorCode,
        @JsonProperty(value = "passes", required = true) PassRequest passes
    ) throws InvalidFormatException {
        try {
            this.orderDate = OffsetDateTime.parse(orderDate);
            this.vendorCode = vendorCode;
            this.passes = passes;
        }
        catch (Exception exception) {
            throw new InvalidFormatException();
        }
    }
}
