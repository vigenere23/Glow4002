package ca.ulaval.glo4002.booking.domain.finance;

import ca.ulaval.glo4002.booking.domain.Price;

public class ProfitCalculator {

    private Price income;
    private Price outcome;
    
    public ProfitCalculator() {
        income = Price.zero();
        outcome = Price.zero();
    }

    public void addIncome(Price income) {
        this.income = this.income.plus(income);
    }

    public void addOutcome(Price outcome) {
        this.outcome = this.outcome.plus(outcome);
    }

    public Price getProfit() {
        return Price.sum(income, outcome);
    }

    public Price getIncome() {
        return income;
    }

    public Price getOutcome() {
        return outcome;
    }
}
