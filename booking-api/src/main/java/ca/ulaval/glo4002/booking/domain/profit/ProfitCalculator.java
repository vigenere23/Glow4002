package ca.ulaval.glo4002.booking.domain.profit;

public class ProfitCalculator {

    private ProfitRepository profitRepository;
    
    public ProfitCalculator(ProfitRepository profitRepository) {
        this.profitRepository = profitRepository;
    }

    public float getIncome() {
		return profitRepository.findIncome();
	}

	public float getOutcome() {
		return profitRepository.findOutcome();
	}

	public float getProfit() {
		return getIncome() - getOutcome();
    }
    
    public void saveIncome(float income) {
        profitRepository.saveIncome(getIncome() + income);
    }

    public void saveOutcome(float outcome) {
        profitRepository.saveOutcome(getOutcome() + outcome);
    }
}