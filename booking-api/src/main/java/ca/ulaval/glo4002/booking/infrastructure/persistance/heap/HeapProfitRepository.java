package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;

public class HeapProfitRepository implements ProfitRepository {

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
    public void saveIncome(Price income) {
        this.income = income;

    }

    @Override
    public void saveOutcome(Price outcome) {
        this.outcome = outcome;
    }
    
}