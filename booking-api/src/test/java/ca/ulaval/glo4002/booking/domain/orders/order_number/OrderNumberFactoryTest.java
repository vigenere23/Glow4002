package ca.ulaval.glo4002.booking.domain.orders.order_number;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.orders.VendorCode;

public class OrderNumberFactoryTest {

    private final static VendorCode SOME_VENDOR_CODE = VendorCode.TEAM;
    
    private OrderNumberFactory orderNumberFactory;

    @BeforeEach
    public void setupOrderNumberFactory() {
        orderNumberFactory = new OrderNumberFactory(new AtomicLong(0));
    }

    @Test
    public void givenOnFirstUse_whenCreating_thenTheNumberIsZero() {
        OrderNumber orderNumber = orderNumberFactory.create(SOME_VENDOR_CODE);
        String expectedValue = SOME_VENDOR_CODE.toString() + "-0";

        assertEquals(expectedValue, orderNumber.toString());
    }

    @Test
    public void givenMultipleCreations_whenCreating_thenTheNumberIsIncrementedByNumberOfCreations() {
        int numberOfCreations = 10;
        OrderNumber lastOrderNumber = null;
        for (int i = 0; i <= numberOfCreations; i++) {
            lastOrderNumber = orderNumberFactory.create(SOME_VENDOR_CODE);
        }

        String expectedValue = SOME_VENDOR_CODE.toString() + "-" + String.valueOf(numberOfCreations);
        assertEquals(expectedValue, lastOrderNumber.toString());
    }
}
