package ca.ulaval.glo4002.booking.application;

import ca.ulaval.glo4002.booking.domain.profit.ProfitCalculator;

public class ProfitUseCase {

	private ProfitCalculator profitCalculator;

	public ProfitUseCase(ProfitCalculator profitCalculator) {
		this.profitCalculator = profitCalculator;
	}

	public float getIncome() {
		return profitCalculator.getIncome();
	}

	public float getOutcome() {
		return profitCalculator.getOutcome();
	}

	public float getProfit() {
		return profitCalculator.getProfit();
	}

}
