package ca.ulaval.glo4002.booking.domain.pass_ordering.passes;

import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.Priceable;

public abstract class Pass implements Priceable {

    protected Long passNumber;
    protected Money price;

    protected Pass() {
        this.passNumber = (long) 0;
    }

    public Money getPrice() {
        return this.price;
    }
}
