package ca.ulaval.glo4002.booking.domain.orders.discounts;

import java.math.RoundingMode;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class NebulaSinglePassDiscount extends OrderDiscount {

    private static final double PERCENTAGE_DISCOUNT = 0.1;
    private static final int NUMBER_OF_ITEMS_REQUIRED = 4;

    @Override
    public Money priceAfterDiscounts(List<Pass> passes, Money totalPrice) {
        long numberOfSupergiantSinglePass = getQuantityOfMatchingPasses(passes, PassOption.SINGLE_PASS, PassCategory.NEBULA);
        Money discount = getDiscount(numberOfSupergiantSinglePass, totalPrice);
        Money priceAfterDiscount = totalPrice.minus(discount);

        return nextDiscount.isPresent()
            ? nextDiscount.get().priceAfterDiscounts(passes, priceAfterDiscount)
            : priceAfterDiscount;
    }

    private Money getDiscount(long numberOfPasses, Money totalPrice) {
        return numberOfPasses >= NUMBER_OF_ITEMS_REQUIRED
            ? totalPrice.multipliedBy(PERCENTAGE_DISCOUNT, RoundingMode.HALF_UP)
            : Money.zero(CurrencyUnit.CAD);
    }
}
