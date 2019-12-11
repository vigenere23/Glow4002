package ca.ulaval.glo4002.booking.domain.orders.order_number;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.orders.VendorCode;

public class OrderNumberTest {

    @Test
    public void whenCreatingOrderNumber_thenConcatensVendorCodeAndNumberWithHyphenBetween() {
        VendorCode vendorCode = VendorCode.TEAM;
        long number = 4568;
        String expectedOrderNumberValue = "TEAM-4568";

        OrderNumber orderNumber = new OrderNumber(vendorCode, number);

        assertEquals(expectedOrderNumberValue, orderNumber.toString());
    }

    @Test
    public void whenCreatingOrderNumberFromString_thenReturnsTheSameValueAsTheGivenString() {
        String stringOrderNumber = "TEAM-4566";
        OrderNumber orderNumber = OrderNumber.fromString(stringOrderNumber);

        assertEquals(stringOrderNumber, orderNumber.toString());
    }

    @Test
    public void givenTwoCreatedOrderNumbersWithDifferentArguments_whenComparingEquality_thenReturnsFalse() {
        OrderNumber orderNumber1 = new OrderNumber(VendorCode.TEAM, 1);
        OrderNumber orderNumber2 = new OrderNumber(VendorCode.TEAM, 2);

        assertNotEquals(orderNumber1, orderNumber2);
    }

    @Test
    public void givenTwoCreatedOrderNumbersWithSameArguments_whenComparingEquality_thenReturnsTrue() {
        OrderNumber orderNumber1 = new OrderNumber(VendorCode.TEAM, 1);
        OrderNumber orderNumber2 = new OrderNumber(VendorCode.TEAM, 1);

        assertEquals(orderNumber1, orderNumber2);
    }
}
