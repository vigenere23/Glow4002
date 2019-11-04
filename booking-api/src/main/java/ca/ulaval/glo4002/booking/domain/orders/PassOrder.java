package ca.ulaval.glo4002.booking.domain.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.orders.discounts.NebulaSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscountFactory;
import ca.ulaval.glo4002.booking.domain.orders.discounts.SupergiantSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.passes.Pass;

public class PassOrder {

    private OrderNumber orderNumber;
    private List<Pass> passes = new ArrayList<Pass>();
    private OrderDiscount orderDiscount;

    public PassOrder(VendorCode vendorCode, List<Pass> passes) {
        this.passes = passes;
        
        orderNumber = new OrderNumber(vendorCode);
        orderDiscount = new OrderDiscountFactory().linkDiscounts(
            new SupergiantSinglePassDiscount(), new NebulaSinglePassDiscount()
        );
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

    public OrderNumber getOrderNumber() {
        return orderNumber;
    }

    public List<Pass> getPasses() {
        return Collections.unmodifiableList(passes);
    }
}
