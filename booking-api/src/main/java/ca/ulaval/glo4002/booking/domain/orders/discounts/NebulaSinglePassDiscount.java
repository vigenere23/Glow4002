package ca.ulaval.glo4002.booking.domain.orders.discounts;

import java.math.RoundingMode;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaSinglePass;

public class NebulaSinglePassDiscount extends OrderDiscount {

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
            .filter(pass -> pass instanceof NebulaSinglePass)
            .count();
    }

    private Money getDiscount(long numberOfWantedPasses, Money totalPrice) {
        if (numberOfWantedPasses > 3) {
            return totalPrice.multipliedBy(0.1, RoundingMode.HALF_UP);
        }
        return Money.zero(CurrencyUnit.CAD);
    }
}