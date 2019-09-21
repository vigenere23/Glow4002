package ca.ulaval.glo4002.booking.domain.pass_ordering.passes;

import ca.ulaval.glo4002.booking.domain.Priceable;

public abstract class Pass implements Priceable {

    protected Long passNumber;
    protected double price;

    protected Pass() {
        this.passNumber = (long) 0;
    }

    public double getPrice() {
        return this.price;
    }
}
