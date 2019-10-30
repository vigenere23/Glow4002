package ca.ulaval.glo4002.booking.domain.profit;

public class ProfitService {

	private ProfitRepository profitRepository;

	public ProfitService(ProfitRepository profitRepository) {
		this.profitRepository = profitRepository;
	}

	public float getIncome() {
		return profitRepository.getIncome();
	}

	public float getOutcome() {
		return profitRepository.getOutcome();
	}
}
