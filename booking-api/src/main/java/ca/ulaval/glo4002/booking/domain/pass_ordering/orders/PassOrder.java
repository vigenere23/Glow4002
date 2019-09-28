package ca.ulaval.glo4002.booking.domain.pass_ordering.orders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.Priceable;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories.PackagePassFactory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories.SinglePassFactory;

public class PassOrder implements Priceable {

    protected List<Pass> passes = new ArrayList<Pass>();
    protected Money totalPrice = Money.parse("USD 0");
    protected LocalDateTime orderDate;
    protected PassCategory passCategory;

    public PassOrder() {
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
        return Money.parse("USD 0");
    }

    public Money getPrice() {
        return this.totalPrice;
    }
}
