package ca.ulaval.glo4002.booking.application;

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

    public double getIncome() {
        return calculateRoundedAmount(profitRepository.findIncome());
    }

    public double getOutcome() {
        return calculateRoundedAmount(profitRepository.findOutcome());
    }

    public double getProfit() {
        Price calculatedProfit = profitCalculator.getProfit(profitRepository.findIncome(), profitRepository.findOutcome());
        return calculateRoundedAmount(calculatedProfit);
    }

    private double calculateRoundedAmount(Price price) {
        return price.getRoundedAmount(NUMBER_OF_DECIMAL);
    }
}
