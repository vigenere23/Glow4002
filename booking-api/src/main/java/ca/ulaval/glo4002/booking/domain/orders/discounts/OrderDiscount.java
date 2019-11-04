package ca.ulaval.glo4002.booking.domain.orders.discounts;

import java.util.List;
import java.util.Optional;

import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public abstract class OrderDiscount {

    protected Optional<OrderDiscount> nextDiscount;

    protected OrderDiscount() {
        this.nextDiscount = Optional.empty();
    }

    public void setNextDiscount(OrderDiscount nextDiscount) {
        this.nextDiscount = Optional.of(nextDiscount);
    }

    protected long getQuantityOfMatchingPasses(List<Pass> passes, PassOption passOption, PassCategory passCategory) {
        return passes
            .stream()
            .filter(pass -> pass.isOfType(passOption, passCategory))
            .count();
    }

    public abstract Money priceAfterDiscounts(List<Pass> passes, Money totalPrice);
}
