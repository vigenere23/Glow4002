package ca.ulaval.glo4002.booking.interfaces.rest.orders.dtos;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;

public class OrderRequest {

    public final OffsetDateTime orderDate;
    public final String vendorCode;
    public final List<PassRequest> passes;

    @JsonCreator
    public OrderRequest(
        @JsonProperty(value = "orderDate", required = true) String orderDate,
        @JsonProperty(value = "vendorCode", required = true) String vendorCode,
        @JsonProperty(value = "passes", required = true) Collection<PassRequest> passes
    ) throws InvalidFormatException {
        this.orderDate = OffsetDateTime.parse(orderDate);
        this.vendorCode = vendorCode;
        if (passes.size() > 1) {
            throw new InvalidFormatException();
        }
        this.passes = new ArrayList<>(passes);
    }
}
