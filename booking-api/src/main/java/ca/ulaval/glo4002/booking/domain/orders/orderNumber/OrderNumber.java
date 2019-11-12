package ca.ulaval.glo4002.booking.domain.orders.orderNumber;

import ca.ulaval.glo4002.booking.domain.orders.VendorCode;

public class OrderNumber {

    private String value;

    public OrderNumber(VendorCode vendorCode, long number) {
        value = String.format("%s-%d", vendorCode.toString(), number);
    }

    private OrderNumber(String value) {
        this.value = value;
    }

    public static OrderNumber of(String value) {
        return new OrderNumber(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof OrderNumber)) return false;

        OrderNumber otherOrderNumber = (OrderNumber) other;
        return value == otherOrderNumber.value;
    }

    public String getValue() {
        return value;
    }
}
