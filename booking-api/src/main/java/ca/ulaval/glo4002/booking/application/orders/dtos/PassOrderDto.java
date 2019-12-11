package ca.ulaval.glo4002.booking.application.orders.dtos;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumber;

public class PassOrderDto {

    public OrderNumber orderNumber;
    public Price price;
    public List<PassDto> passes;

    public PassOrderDto(OrderNumber orderNumber, Price orderPrice, List<PassDto> passes) {
        this.orderNumber = orderNumber;
        this.price = orderPrice;
        this.passes = passes;
    }
}
