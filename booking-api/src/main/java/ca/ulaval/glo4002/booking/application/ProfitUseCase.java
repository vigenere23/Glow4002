package ca.ulaval.glo4002.booking.application;

import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;

public class ProfitUseCase {

	private ProfitRepository profitRepository;

	public ProfitUseCase(ProfitRepository profitRepository) {
		this.profitRepository = profitRepository;
	}

	public float getIncome() {
		return profitRepository.getIncome();
	}

	public float getOutcome() {
		return profitRepository.getOutcome();
	}

	public float getProfit() {
		return 0f;
	}

}
