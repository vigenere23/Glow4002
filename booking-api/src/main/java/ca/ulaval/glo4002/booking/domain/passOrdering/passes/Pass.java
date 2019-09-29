package ca.ulaval.glo4002.booking.domain.passOrdering.passes;

import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.Priceable;

public abstract class Pass implements Priceable {

    protected Long passNumber;
    protected Money price;

    protected Pass() {
        this.passNumber = 0L;
    }

    public Money getPrice() {
        return this.price;
    }

    public Long getId() {
        return this.passNumber;
    }

    public void setId(Long id) {
        this.passNumber = id;
    }
}
