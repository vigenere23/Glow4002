package ca.ulaval.glo4002.booking.domain.orders.orderNumber;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.orders.VendorCode;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderNumberFactoryTest {

    private OrderNumberFactory orderNumberFactory;

    private static final VendorCode SOME_VENDOR_CODE = VendorCode.TEAM;

    @BeforeEach
    public void setupOrderNumberFactory() {
        orderNumberFactory = new OrderNumberFactory(new AtomicLong(0));
    }

    @Test
    public void givenOnFirstUse_whenCreating_thenTheNumberIsZero() {
        OrderNumber orderNumber = orderNumberFactory.create(SOME_VENDOR_CODE);
        String expectedValue = SOME_VENDOR_CODE.toString() + "-0";
        assertThat(orderNumber.getValue()).isEqualTo(expectedValue);
    }

    @Test
    public void givenMultipleCreation_whenCreating_thenTheNumberIsIncrementedByNumberOfCreations() {
        int numberOfCreations = 10;
        OrderNumber lastOrderNumber = null;
        for (int i = 0; i <= numberOfCreations; i++) {
            lastOrderNumber = orderNumberFactory.create(SOME_VENDOR_CODE);
        }

        String expectedValue = SOME_VENDOR_CODE.toString() + "-" + String.valueOf(numberOfCreations);
        assertThat(lastOrderNumber.getValue()).isEqualTo(expectedValue);
    }
}
