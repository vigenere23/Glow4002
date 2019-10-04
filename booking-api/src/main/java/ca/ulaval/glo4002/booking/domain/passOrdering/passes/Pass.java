package ca.ulaval.glo4002.booking.domain.passOrdering.passes;

import java.time.OffsetDateTime;

import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.Priceable;

public abstract class Pass implements Priceable {

    protected Long passNumber;
    protected Money price;
    protected OffsetDateTime startDate;
    protected OffsetDateTime endDate;

    protected Pass(OffsetDateTime startDate, OffsetDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
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
    
    public long getPassNumber() {
        return this.passNumber;
    }

    public abstract PassOption getPassOption();
    public abstract PassCategory getPassCategory();
}
