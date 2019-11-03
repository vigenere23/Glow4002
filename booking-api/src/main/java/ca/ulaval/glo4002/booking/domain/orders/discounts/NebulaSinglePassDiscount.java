package ca.ulaval.glo4002.booking.domain.orders.discounts;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaSinglePass;

public class NebulaSinglePassDiscount extends OrderDiscount {

    @Override
    public Price getPriceAfterDiscounts(List<Pass> passes, Price totalPrice) {
        long numberOfSupergiantSinglePass = getNumberOfWantedObjects(passes);
        Price discount = getDiscount(numberOfSupergiantSinglePass, totalPrice);
        Price newPrice = totalPrice.minus(discount);

        if (nextDiscount != null) {
            return nextDiscount.getPriceAfterDiscounts(passes, newPrice);
        }
        return newPrice;
    }

    private long getNumberOfWantedObjects(List<Pass> passes) {
        return passes
            .stream()
            .filter(pass -> pass instanceof NebulaSinglePass)
            .count();
    }

    private Price getDiscount(long numberOfWantedPasses, Price totalPrice) {
        if (numberOfWantedPasses > 3) {
            return totalPrice.multipliedBy(0.1);
        }
        return Price.zero();
    }
}