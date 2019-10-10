package ca.ulaval.glo4002.booking.domain.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.orders.discounts.NebulaSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.SupergiantSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.passes.Pass;

public class PassOrder {

    private List<Pass> passes = new ArrayList<Pass>();
    private OrderDiscount orderDiscount;

    private ID id;

    public PassOrder(List<Pass> passes) {
        this.passes = passes;
        
        orderDiscount = new SupergiantSinglePassDiscount();
        orderDiscount.setNextDiscount(new NebulaSinglePassDiscount());
    }

    public Money getPrice() {
        return calculateTotalPrice();
    }

    private Money calculateTotalPrice() {
        Money priceBeforeDiscounts = passes
            .stream()
            .map(Pass::getPrice)
            .reduce(Money.zero(CurrencyUnit.CAD), (subtotal, price) -> subtotal.plus(price));

        if (orderDiscount == null) {
            return priceBeforeDiscounts;
        }
        return orderDiscount.priceAfterDiscounts(Collections.unmodifiableList(passes), priceBeforeDiscounts);
    }

    public ID getOrderNumber() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public List<Pass> getPasses() {
        return Collections.unmodifiableList(passes);
    }
}
