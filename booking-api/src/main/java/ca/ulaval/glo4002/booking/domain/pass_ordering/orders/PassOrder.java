package ca.ulaval.glo4002.booking.domain.pass_ordering.orders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.Priceable;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories.PackagePassFactory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories.SinglePassFactory;

public class PassOrder implements Priceable {

    protected List<Pass> passes = new ArrayList<Pass>();
    protected double totalPrice;
    protected LocalDateTime orderDate;

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
        this.totalPrice = calculateTotalPrice() - calculateRebates();
    }

    protected double calculateTotalPrice() {
        return this.passes
            .stream()
            .map(Pass::getPrice)
            .reduce(0.0, (partialTotal, price) -> partialTotal + price);
    }

    protected double calculateRebates() {
        return 0;
    }

    public double getPrice() {
        return this.totalPrice;
    }
}
