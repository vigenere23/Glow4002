package ca.ulaval.glo4002.booking.domain.profit;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.Price;

public class ProfitSaver implements IncomeSaver, OutcomeSaver {

    @Inject private ProfitRepository profitRepository;

    public void addIncome(Price income) {
        profitRepository.addIncome(income);
    }

    public void addOutcome(Price outcome) {
        profitRepository.addOutcome(outcome);
    }
}
