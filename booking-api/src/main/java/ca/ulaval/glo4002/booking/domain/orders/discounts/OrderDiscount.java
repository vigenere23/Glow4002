package ca.ulaval.glo4002.booking.domain.orders.discounts;

import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public abstract class OrderDiscount {

    private Optional<OrderDiscount> nextDiscount;
    private PassOption passOption;
    private PassCategory passCategory;

    protected OrderDiscount(PassOption passOption, PassCategory passCategory) {
        this.passOption = passOption;
        this.passCategory = passCategory;

        nextDiscount = Optional.empty();
    }

    public void setNextDiscount(OrderDiscount nextDiscount) {
        this.nextDiscount = Optional.of(nextDiscount);
    }

    public Price getPriceAfterDiscounts(List<Pass> passes, Price totalPrice) {
        long numberOfMatchingPasses = getQuantityOfMatchingPasses(passes);
        Price discount = getDiscountAmount(numberOfMatchingPasses, totalPrice);
        Price priceAfterDiscount = totalPrice.minus(discount);

        return nextDiscount.isPresent()
            ? nextDiscount.get().getPriceAfterDiscounts(passes, priceAfterDiscount)
            : priceAfterDiscount;
    }

    private long getQuantityOfMatchingPasses(List<Pass> passes) {
        return passes
            .stream()
            .filter(pass -> pass.isOfType(passOption, passCategory))
            .count();
    }

    protected abstract Price getDiscountAmount(long numberOfMatchingPasses, Price totalPrice);
}
