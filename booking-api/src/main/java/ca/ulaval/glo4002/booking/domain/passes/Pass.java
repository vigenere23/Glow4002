package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;

import org.joda.money.Money;

public abstract class Pass {

    protected PassNumber passNumber;
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

    public void setPassNumber(PassNumber id) {
        this.passNumber = id;
    }
    
    public PassNumber getPassNumber() {
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
