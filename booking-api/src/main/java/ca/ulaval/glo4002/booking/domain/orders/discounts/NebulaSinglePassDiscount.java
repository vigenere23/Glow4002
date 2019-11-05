package ca.ulaval.glo4002.booking.domain.orders.discounts;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class NebulaSinglePassDiscount extends OrderDiscount {

    private static final double PERCENTAGE_DISCOUNT = 0.1;
    private static final int NUMBER_OF_ITEMS_REQUIRED = 4;

    @Override
    public Price getPriceAfterDiscounts(List<Pass> passes, Price totalPrice) {
        long numberOfSupergiantSinglePass = getQuantityOfMatchingPasses(passes, PassOption.SINGLE_PASS, PassCategory.NEBULA);
        Price discount = getDiscount(numberOfSupergiantSinglePass, totalPrice);
        Price priceAfterDiscount = totalPrice.minus(discount);

        return nextDiscount.isPresent()
            ? nextDiscount.get().getPriceAfterDiscounts(passes, priceAfterDiscount)
            : priceAfterDiscount;
    }

    private Price getDiscount(long numberOfPasses, Price totalPrice) {
        return numberOfPasses >= NUMBER_OF_ITEMS_REQUIRED
            ? totalPrice.multipliedBy(PERCENTAGE_DISCOUNT)
            : Price.zero();
    }
}
