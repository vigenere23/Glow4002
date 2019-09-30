package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.Priceable;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts.OrderDiscount;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;

public abstract class PassOrder implements Priceable {

    protected List<Pass> passes = new ArrayList<Pass>();
    protected Money totalPrice = Money.zero(CurrencyUnit.CAD);
    protected OffsetDateTime orderDate;
    protected PassCategory passCategory;
    protected OrderDiscount orderDiscount;

    private Long id;

    public PassOrder(OffsetDateTime orderDate) {
        this.orderDate = orderDate;
    }

    protected Money calculateTotalPrice() {
        Money priceBeforeDiscounts = this.passes
            .stream()
            .map(Pass::getPrice)
            .reduce(Money.zero(CurrencyUnit.CAD), (subtotal, price) -> subtotal.plus(price));

        if (this.orderDiscount == null) {
            return priceBeforeDiscounts;
        }
        
        return this.orderDiscount.priceAfterDiscounts(Collections.unmodifiableList(this.passes), priceBeforeDiscounts);
    }

    public Money getPrice() {
        return calculateTotalPrice();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
