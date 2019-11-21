package ca.ulaval.glo4002.booking.application.profit;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;

public class ProfitUseCase {

    private static final int NUMBER_OF_DECIMAL = 2;

    private ProfitCalculator profitCalculator;
    private ProfitRepository profitRepository;

    public ProfitUseCase(ProfitCalculator profitCalculator, ProfitRepository profitRepository) {
        this.profitCalculator = profitCalculator;
        this.profitRepository = profitRepository;
    }

    public float getIncome() {
        return calculateRoundedAmount(profitRepository.findIncome());
    }

    public float getOutcome() {
        return calculateRoundedAmount(profitRepository.findOutcome());
    }

    public float getProfit() {
        Price calculatedProfit = profitCalculator.getProfit(profitRepository.findIncome(), profitRepository.findOutcome());
        return calculateRoundedAmount(calculatedProfit);
    }

    private float calculateRoundedAmount(Price price) {
        return price.getRoundedAmount(NUMBER_OF_DECIMAL);
    }
}
