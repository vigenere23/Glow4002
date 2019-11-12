package ca.ulaval.glo4002.booking.domain.orders.orderNumber;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.orders.VendorCode;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderNumberTest {

    @Test
    public void whenCreatingOrderNumber_itConcatensTheVendorCodeAndTheNumberWithHyphenBetween() {
        VendorCode vendorCode = VendorCode.TEAM;
        long number = 4568;
        String expectedOrderNumberValue = "TEAM-4568";

        OrderNumber orderNumber = new OrderNumber(vendorCode, number);

        assertThat(orderNumber.getValue()).isEqualTo(expectedOrderNumberValue);
    }

    @Test
    public void whenCreatingOrderNumberFromString_itReturnsTheSameValueAsTheGivenString() {
        String stringOrderNumber = "TEAM-4566";
        OrderNumber orderNumber = OrderNumber.of(stringOrderNumber);
        assertThat(orderNumber.getValue()).isEqualTo(stringOrderNumber);
    }

    @Test
    public void givenTwoCreatedOrderNumbersWithDifferentArguments_whenComparingEquality_itReturnsFalse() {
        OrderNumber orderNumber1 = new OrderNumber(VendorCode.TEAM, 1);
        OrderNumber orderNumber2 = new OrderNumber(VendorCode.TEAM, 2);
        assertThat(orderNumber1).isNotEqualTo(orderNumber2);
    }

    @Test
    public void givenTwoCreatedOrderNumbersWithSameArguments_whenComparingEquality_itReturnsTrue() {
        OrderNumber orderNumber1 = new OrderNumber(VendorCode.TEAM, 1);
        OrderNumber orderNumber2 = new OrderNumber(VendorCode.TEAM, 1);
        assertThat(orderNumber1).isEqualTo(orderNumber2);
    }
}
