package ca.ulaval.glo4002.booking.domain.passOrdering.passes;

import java.time.OffsetDateTime;

import org.joda.money.Money;

public abstract class Pass {

    protected Long passNumber;
    protected Money price;
    protected OffsetDateTime startDate;
    protected OffsetDateTime endDate;

    protected Pass(OffsetDateTime startDate, OffsetDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Money getPrice() {
        return price;
    }

    public Long getId() {
        return passNumber;
    }

    public void setId(Long id) {
        this.passNumber = id;
    }
    
    public long getPassNumber() {
        return passNumber;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public abstract PassOption getPassOption();
    public abstract PassCategory getPassCategory();
}
