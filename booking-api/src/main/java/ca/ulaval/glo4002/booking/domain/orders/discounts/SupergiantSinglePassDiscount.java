package ca.ulaval.glo4002.booking.domain.orders.discounts;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class SupergiantSinglePassDiscount extends OrderDiscount {

    private static final Price ABSOLUTE_DISCOUNT = new Price(10000);
    private static final int NUMBER_OF_ITEMS_REQUIRED = 5;

    public SupergiantSinglePassDiscount() {
        super(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT);
    }

    @Override
    protected Price getDiscountAmount(long numberOfPasses, Price totalPrice) {
        return numberOfPasses >= NUMBER_OF_ITEMS_REQUIRED
            ? ABSOLUTE_DISCOUNT.multipliedBy(numberOfPasses)
            : Price.zero();
    }
}
