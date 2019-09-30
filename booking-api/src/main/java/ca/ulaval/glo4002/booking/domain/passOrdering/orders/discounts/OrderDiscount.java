package ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts;

import java.util.List;

import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;

public abstract class OrderDiscount {

    protected OrderDiscount next;

    public void setNext(OrderDiscount next) {
        this.next = next;
    }

    public abstract Money priceAfterDiscounts(List<Pass> passes, Money totalPrice);
}
