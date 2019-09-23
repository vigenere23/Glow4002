package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import java.time.LocalDateTime;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.Priceable;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.PackagePassFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;

public class PassOrder implements Priceable {

    private List<Pass> passes;
    private Money totalPrice;
    private LocalDateTime orderDate;

    private PassOrder() {
        this.orderDate = LocalDateTime.now();
    }

    public PassOrder(PackagePassFactory passFactory, PassCategory passCategory) {
        this();
        passes.add(passFactory.newPass(passCategory));
    }

    public PassOrder(SinglePassFactory passFactory, PassCategory passCategory, List<LocalDateTime> eventDates) {
        this();
        for (LocalDateTime eventDate : eventDates) {
            passes.add(passFactory.newPass(passCategory, eventDate));
        }
        this.updateTotalPrice();
    }

    private void updateTotalPrice() {
        this.totalPrice = calculateTotalPrice();
        this.totalPrice = this.totalPrice.minus(calculateRebates());
    }

    private Money calculateTotalPrice() {
        return this.passes
            .stream()
            .map(Pass::getPrice)
            .reduce(Money.of(CurrencyUnit.CAD, 0), (partialTotal, price) -> {
                return partialTotal.plus(price);
            });
    }

    private Money calculateRebates() {
        // TODO implement
        return Money.of(CurrencyUnit.CAD, 0);
    }

    public Money getPrice() {
        return this.totalPrice;
    }
}
