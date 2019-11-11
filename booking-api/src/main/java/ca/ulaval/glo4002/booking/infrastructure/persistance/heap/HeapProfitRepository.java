package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;

public class HeapProfitRepository implements ProfitRepository {

    private float income = 0f;
    private float outcome = 0f;

    @Override
    public float findIncome() {
        return income;
    }

    @Override
    public float findOutcome() {
        return outcome;
    }

    @Override
    public void saveIncome(float income) {
        this.income = income;

    }

    @Override
    public void saveOutcome(float outcome) {
        this.outcome = outcome;
    }
    
}