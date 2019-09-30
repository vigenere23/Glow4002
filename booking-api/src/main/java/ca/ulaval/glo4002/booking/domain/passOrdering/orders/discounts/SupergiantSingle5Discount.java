package ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts;

import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantSinglePass;

public class SupergiantSingle5Discount extends OrderDiscount {

    @Override
    public Money priceAfterDiscounts(List<Pass> passes, Money totalPrice) {
        long numberOfSupergiantSinglePass = getNumberOfWantedObjects(passes);
        Money discount = getDiscount(numberOfSupergiantSinglePass, totalPrice);
        Money newPrice = totalPrice.minus(discount);

        if (this.next != null) {
            return this.next.priceAfterDiscounts(passes, newPrice);
        }

        return newPrice;
    }

    private long getNumberOfWantedObjects(List<Pass> passes) {
        return passes
            .stream()
            .filter(pass -> pass instanceof SupergiantSinglePass)
            .count();
    }

    private Money getDiscount(long numberOfWantedPasses, Money totalPrice) {
        if (numberOfWantedPasses >= 5) {
            return Money.of(CurrencyUnit.CAD, numberOfWantedPasses * 10000);
        }
        
        return Money.zero(CurrencyUnit.CAD);
    }
}
