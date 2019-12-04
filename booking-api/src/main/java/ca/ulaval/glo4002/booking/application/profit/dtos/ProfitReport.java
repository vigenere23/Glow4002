package ca.ulaval.glo4002.booking.application.profit.dtos;

import ca.ulaval.glo4002.booking.domain.Price;

public class ProfitReport {

    private Price income;
    private Price outcome;

    public ProfitReport(Price income, Price outcome) {
        this.income = income;
        this.outcome = outcome;
    }

    public Price calculateProfit() {
        return income.minus(outcome);
    }

    public Price getIncome() {
        return income;
    }

    public Price getOutcome() {
        return outcome;
    }
}
