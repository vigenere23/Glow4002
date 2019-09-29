package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.Priceable;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.PackagePassFactory;

public class PassOrder implements Priceable {

    protected List<Pass> passes = new ArrayList<Pass>();
    protected Money totalPrice = Money.zero(CurrencyUnit.CAD);
    protected OffsetDateTime orderDate;
    protected PassCategory passCategory;

    public PassOrder() {
        this.orderDate = OffsetDateTime.now();
    }

    public PassOrder(PackagePassFactory passFactory, PassCategory passCategory) {
        this();
        passes.add(passFactory.create(passCategory));
    }

    public PassOrder(SinglePassFactory passFactory, PassCategory passCategory, List<OffsetDateTime> eventDates) {
        this();
        for (OffsetDateTime eventDate : eventDates) {
            passes.add(passFactory.create(passCategory, eventDate));
        }
        this.updateTotalPrice();
    }

    protected void updateTotalPrice() {
        calculateTotalPrice();
        this.totalPrice.minus(calculateRebates());
    }

    protected void calculateTotalPrice() {
        for (Pass pass : passes) {
            this.totalPrice = this.totalPrice.plus(pass.getPrice());
        }
    }

    protected Money calculateRebates() {
        return Money.zero(CurrencyUnit.CAD);
    }

    public Money getPrice() {
        return this.totalPrice;
    }
}
