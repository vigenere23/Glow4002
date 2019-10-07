package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;

import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.orders.ID;

public abstract class Pass {

    protected ID passNumber;
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

    public void setPassNumber(ID id) {
        this.passNumber = id;
    }
    
    public ID getPassNumber() {
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
