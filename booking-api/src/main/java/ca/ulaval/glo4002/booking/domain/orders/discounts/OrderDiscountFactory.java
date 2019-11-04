package ca.ulaval.glo4002.booking.domain.orders.discounts;

public class OrderDiscountFactory {

    public OrderDiscount linkDiscounts(OrderDiscount...discounts) {
        if (discounts.length == 0) {
            throw new IllegalArgumentException("At least one discount must be present");
        }
        for (int i = 0; i < discounts.length - 1; i++) {
            discounts[i].setNextDiscount(discounts[i + 1]);
        }
        return discounts[0];
    }
}
