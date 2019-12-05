package ca.ulaval.glo4002.booking.domain.orders.discounts;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class NebulaSinglePassDiscount extends OrderDiscount {

    private static final double PERCENTAGE_DISCOUNT = 0.1;
    private static final int NUMBER_OF_ITEMS_REQUIRED = 4;

    public NebulaSinglePassDiscount() {
        super(PassOption.SINGLE_PASS, PassCategory.NEBULA);
    }

    @Override
    protected Price getDiscountAmount(long numberOfPasses, Price totalPrice) {
        return numberOfPasses >= NUMBER_OF_ITEMS_REQUIRED
            ? totalPrice.multipliedBy(PERCENTAGE_DISCOUNT)
            : Price.zero();
    }
}
