package ca.ulaval.glo4002.booking.application;

import ca.ulaval.glo4002.booking.domain.profit.ProfitCalculator;

public class ProfitUseCase {

    private static final int NUMBER_OF_DECIMAL = 2;

	private ProfitCalculator profitCalculator;

	public ProfitUseCase(ProfitCalculator profitCalculator) {
		this.profitCalculator = profitCalculator;
	}

	public double getIncome() {
		return profitCalculator.getIncome().getRoundedAmount(NUMBER_OF_DECIMAL);
	}

	public double getOutcome() {
		return profitCalculator.getOutcome().getRoundedAmount(NUMBER_OF_DECIMAL);
	}

	public double getProfit() {
		return profitCalculator.getProfit().getRoundedAmount(NUMBER_OF_DECIMAL);
	}

}
