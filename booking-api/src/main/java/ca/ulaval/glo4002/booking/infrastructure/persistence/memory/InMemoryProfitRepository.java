package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;

public class InMemoryProfitRepository implements ProfitRepository {

    private Price income = new Price(0);
    private Price outcome = new Price(0);

    @Override
    public Price findIncome() {
        return income;
    }

    @Override
    public Price findOutcome() {
        return outcome;
    }

    @Override
    public void addIncome(Price income) {
        this.income = this.income.plus(income);

    }

    @Override
    public void addOutcome(Price outcome) {
        this.outcome = this.outcome.plus(outcome);
    }
}
