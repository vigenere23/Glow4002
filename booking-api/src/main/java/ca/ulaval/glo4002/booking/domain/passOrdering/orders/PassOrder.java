package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.Priceable;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts.NebulaSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts.OrderDiscount;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts.SupergiantSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;

public class PassOrder implements Priceable {

    private OffsetDateTime orderDate;
    private String vendorCode;
    private List<Pass> passes = new ArrayList<Pass>();
    private OrderDiscount orderDiscount;

    private Long id;

    public PassOrder(OffsetDateTime orderDate, String vendorCode, List<Pass> passes) {
        this.orderDate = orderDate;
        this.vendorCode = vendorCode;
        this.passes = passes;
        this.orderDiscount = new SupergiantSinglePassDiscount();
        this.orderDiscount.setNextDiscount(new NebulaSinglePassDiscount());
    }

    public Money getPrice() {
        return calculateTotalPrice();
    }

    private Money calculateTotalPrice() {
        Money priceBeforeDiscounts = this.passes
            .stream()
            .map(Pass::getPrice)
            .reduce(Money.zero(CurrencyUnit.CAD), (subtotal, price) -> subtotal.plus(price));

        if (this.orderDiscount == null) {
            return priceBeforeDiscounts;
        }
        
        return this.orderDiscount.priceAfterDiscounts(Collections.unmodifiableList(this.passes), priceBeforeDiscounts);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Pass> getPasses() {
        return Collections.unmodifiableList(this.passes);
    }
}
