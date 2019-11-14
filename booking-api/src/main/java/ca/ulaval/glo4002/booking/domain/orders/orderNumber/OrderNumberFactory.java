package ca.ulaval.glo4002.booking.domain.orders.orderNumber;

import java.util.concurrent.atomic.AtomicLong;

import ca.ulaval.glo4002.booking.domain.orders.VendorCode;

public class OrderNumberFactory {

    private AtomicLong numberGenerator;

    public OrderNumberFactory(AtomicLong numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public OrderNumber create(VendorCode vendorCode) {
        return new OrderNumber(vendorCode, numberGenerator.getAndIncrement());
    }
}
