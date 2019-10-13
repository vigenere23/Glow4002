package ca.ulaval.glo4002.booking.domain.orders;

import java.util.concurrent.atomic.AtomicLong;

public class OrderNumber {

    private String value;
    private static final AtomicLong incrementor = new AtomicLong(0); 

    public OrderNumber(VendorCode vendorCode) {
        value = String.format("%s-%d", vendorCode.toString(), incrementor.getAndIncrement());
    }

    private OrderNumber(String value) {
        this.value = value;
    }

    public static OrderNumber of(String value) {
        return new OrderNumber(value);
    }

    public String getValue() {
        return value;
    }

    public boolean equals(OrderNumber other) {
        return value.equals(other.value);
    }
}
