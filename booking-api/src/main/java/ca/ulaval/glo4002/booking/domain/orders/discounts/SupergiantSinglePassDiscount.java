package ca.ulaval.glo4002.booking.domain.orders.discounts;

import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class SupergiantSinglePassDiscount extends OrderDiscount {

    private static final double ABSOLUTE_DISCOUNT = 10000;
    private static final int NUMBER_OF_ITEMS_REQUIRED = 5;

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
                    && pass.getPassCategory() == PassCategory.SUPERGIANT;
            })
            .count();
    }

    private Money getDiscount(long numberOfWantedPasses, Money totalPrice) {
        if (numberOfWantedPasses >= NUMBER_OF_ITEMS_REQUIRED) {
            return Money.of(CurrencyUnit.CAD, numberOfWantedPasses * ABSOLUTE_DISCOUNT);
        }
        return Money.zero(CurrencyUnit.CAD);
    }
}
