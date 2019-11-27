package ca.ulaval.glo4002.booking.domain.profit;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.Price;

public class ProfitSaver implements IncomeSaver, OutcomeSaver {

    @Inject private ProfitRepository profitRepository;

    public void saveIncome(Price income) {
        profitRepository.updateIncome(getIncome().plus(income));
    }

    public void saveOutcome(Price outcome) {
        profitRepository.updateOutcome(getOutcome().plus(outcome));
    }

    private Price getIncome() {
        return profitRepository.findIncome();
    }

    private Price getOutcome() {
        return profitRepository.findOutcome();
    }
}