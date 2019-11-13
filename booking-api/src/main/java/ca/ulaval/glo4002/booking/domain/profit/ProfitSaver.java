package ca.ulaval.glo4002.booking.domain.profit;

import ca.ulaval.glo4002.booking.domain.Price;

public class ProfitSaver implements IncomeSaver, OutcomeSaver {

    private ProfitRepository profitRepository;
    
    public ProfitSaver(ProfitRepository profitRepository) {
        this.profitRepository = profitRepository;
    }

    public void saveIncome(Price income) {
        profitRepository.saveIncome(getIncome().plus(income));
    }

    public void saveOutcome(Price outcome) {
        profitRepository.saveOutcome(getOutcome().plus(outcome));
    }

    private Price getIncome() {
        return profitRepository.findIncome();
    }

    private Price getOutcome() {
        return profitRepository.findOutcome();
    }
}