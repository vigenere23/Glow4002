package ca.ulaval.glo4002.booking.interfaces.rest.orders.orderDTOs;

public class OrderDTO {

    public String orderDate;
    public String vendorCode;
    public PassDTO passes = new PassDTO();
}