package ca.ulaval.glo4002.booking.application.profit;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.profit.ProfitReport;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;

public class ProfitUseCase {

    @Inject private ProfitRepository profitRepository;

    public ProfitReport generateProfitReport() {
        return new ProfitReport(
            profitRepository.findIncome(),
            profitRepository.findOutcome()
        );
    }
}
