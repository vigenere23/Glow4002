package ca.ulaval.glo4002.booking.application.orders.dtos;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumber;

public class PassOrderDto {

    public String orderNumber;
    public float price;
    public List<PassDto> passes;

    public PassOrderDto() {}

    public PassOrderDto(OrderNumber orderNumber, Price orderPrice, List<PassDto> passes) {
        this.orderNumber = orderNumber.toString();
        this.price = orderPrice.getRoundedAmountFromCurrencyScale();
        this.passes = passes;
    }
}
