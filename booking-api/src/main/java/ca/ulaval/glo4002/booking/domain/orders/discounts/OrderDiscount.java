package ca.ulaval.glo4002.booking.domain.orders.discounts;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.Pass;

public abstract class OrderDiscount {

    protected OrderDiscount nextDiscount;

    public void setNextDiscount(OrderDiscount nextDiscount) {
        this.nextDiscount = nextDiscount;
    }

    public abstract Price priceAfterDiscounts(List<Pass> passes, Price totalPrice);
}
