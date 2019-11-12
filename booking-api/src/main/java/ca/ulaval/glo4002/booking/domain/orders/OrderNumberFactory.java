package ca.ulaval.glo4002.booking.domain.orders;

import java.util.concurrent.atomic.AtomicLong;

public class OrderNumberFactory {

    private AtomicLong incrementor;

    public OrderNumberFactory(AtomicLong incrementor) {
        this.incrementor = incrementor;
    }

    public OrderNumber create(VendorCode vendorCode) {
        return new OrderNumber(vendorCode, incrementor.getAndIncrement());
    }
}
