package ca.ulaval.glo4002.booking.interfaces.rest.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.interfaces.exceptions.ApiException;
import ca.ulaval.glo4002.booking.interfaces.rest.orders.orderDTOs.OrderDTO;
import ca.ulaval.glo4002.booking.interfaces.rest.orders.orderDTOs.PassDTO;

public class OrderRequest {

    public OrderDTO order = new OrderDTO();

    @JsonCreator
    public OrderRequest(

        @JsonProperty(value = "orderDate") String orderDate,
        @JsonProperty(value = "vendorCode") String vendorCode,
        @JsonProperty(value = "passes") PassDTO passes) throws ApiException {

            this.order.orderDate = orderDate; 
            this.order.vendorCode = vendorCode;
            this.order.passes.passCategory = passes.passCategory;
            this.order.passes.passOption = passes.passOption;
            this.order.passes.eventDates = passes.eventDates;
        }
}
