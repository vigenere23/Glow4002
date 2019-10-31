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
        long numberOfSupergiantSinglePass = getNumberOfWantedObjects(passes);
        Money discount = getDiscount(numberOfSupergiantSinglePass, totalPrice);
        Money newPrice = totalPrice.minus(discount);

        if (nextDiscount != null) {
            return nextDiscount.priceAfterDiscounts(passes, newPrice);
        }
        return newPrice;
    }

    private long getNumberOfWantedObjects(List<Pass> passes) {
        return passes
            .stream()
            .filter(pass -> {
                return pass.getPassOption() == PassOption.SINGLE_PASS
                    && pass.getPassCategory() == PassCategory.NEBULA;
            })
            .count();
    }

    private Money getDiscount(long numberOfWantedPasses, Money totalPrice) {
        if (numberOfWantedPasses >= NUMBER_OF_ITEMS_REQUIRED) {
            return totalPrice.multipliedBy(PERCENTAGE_DISCOUNT, RoundingMode.HALF_UP);
        }
        return Money.zero(CurrencyUnit.CAD);
    }
}