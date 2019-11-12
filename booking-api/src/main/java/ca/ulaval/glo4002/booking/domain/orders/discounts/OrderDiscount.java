package ca.ulaval.glo4002.booking.domain.orders.discounts;

import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.Price;
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
            .filter(pass -> pass.isOfOption(passOption) && pass.isOfCategory(passCategory))
            .count();
    }

    public abstract Price getPriceAfterDiscounts(List<Pass> passes, Price totalPrice);
}
