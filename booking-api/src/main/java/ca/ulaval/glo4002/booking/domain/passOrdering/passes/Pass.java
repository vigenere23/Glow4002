package ca.ulaval.glo4002.booking.domain.passOrdering.passes;

import java.time.LocalDate;

import org.joda.money.Money;

public abstract class Pass {

    protected Long passNumber;
    protected Money price;
    protected LocalDate startDate;
    protected LocalDate endDate;

    protected Pass(LocalDate startDate, LocalDate endDate) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public abstract PassOption getPassOption();
    public abstract PassCategory getPassCategory();
}
