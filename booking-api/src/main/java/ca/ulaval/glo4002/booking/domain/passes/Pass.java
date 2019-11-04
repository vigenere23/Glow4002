package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;

import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;

public class Pass {

    private PassNumber passNumber;
    private Money price;
    private PassOption passOption;
    private PassCategory passCategory;
    private LocalDate startDate;
    private LocalDate endDate;

    public Pass(Glow4002 festival, PassOption passOption, PassCategory passCategory, Money price, LocalDate startDate, LocalDate endDate) {
        festival.validateEventDates(startDate, endDate);

        this.passNumber = new PassNumber();
        this.passOption = passOption;
        this.passCategory = passCategory;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isOfType(PassOption passOption, PassCategory passCategory) {
        return this.passOption == passOption && this.passCategory == passCategory;
    }

    public Money getPrice() {
        return price;
    }

    public PassNumber getPassNumber() {
        return passNumber;
    }

    public PassOption getPassOption() {
        return passOption;
    }

    public PassCategory getPassCategory() {
        return passCategory;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
