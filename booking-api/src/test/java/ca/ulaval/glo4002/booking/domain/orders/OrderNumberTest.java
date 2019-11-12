package ca.ulaval.glo4002.booking.domain.orders;

import org.junit.jupiter.api.Test;

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
}
