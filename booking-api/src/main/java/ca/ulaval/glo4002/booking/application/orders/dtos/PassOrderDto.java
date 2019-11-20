package ca.ulaval.glo4002.booking.application.orders.dtos;

import java.util.List;

import ca.ulaval.glo4002.booking.application.orders.dtos.PassDto;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;

public class PassOrderDto {

    public String orderNumber;
    public float orderPrice;
    public List<PassDto> passes;

    public PassOrderDto() {}

    public PassOrderDto(OrderNumber orderNumber, Price orderPrice, List<PassDto> passes) {
        this.orderNumber = orderNumber.getValue();
        this.orderPrice = orderPrice.getRoundedAmount(2);
        this.passes = passes;
    }
}
