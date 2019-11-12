package ca.ulaval.glo4002.booking.domain.profit;

import ca.ulaval.glo4002.booking.domain.Price;

public class ProfitCalculator {

    private ProfitRepository profitRepository;
    
    public ProfitCalculator(ProfitRepository profitRepository) {
        this.profitRepository = profitRepository;
    }

    public Price getIncome() {
		return profitRepository.findIncome();
	}

	public Price getOutcome() {
		return profitRepository.findOutcome();
	}

	public Price getProfit() {
		return getIncome().minus(getOutcome());
    }
    
    public void saveIncome(Price income) {
        profitRepository.saveIncome(getIncome().plus(income));
    }

    public void saveOutcome(Price outcome) {
        profitRepository.saveOutcome(getOutcome().plus(outcome));
    }
}