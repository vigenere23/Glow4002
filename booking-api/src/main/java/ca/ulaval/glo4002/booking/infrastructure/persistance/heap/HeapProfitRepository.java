package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;

public class HeapProfitRepository implements ProfitRepository {

    private float income = 10.00f;
    private float outcome = 100.00f;

    @Override
    public float getIncome() {
        return income;
    }

    @Override
    public float getOutcome() {
        return outcome;
    }

    @Override
    public void saveIncome(float income) {
        this.income += income;

    }

    @Override
    public void saveOutcome(float outcome) {
        this.outcome += outcome;
    }
    
}