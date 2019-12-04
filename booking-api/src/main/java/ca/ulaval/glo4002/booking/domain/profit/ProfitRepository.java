package ca.ulaval.glo4002.booking.domain.profit;

import ca.ulaval.glo4002.booking.domain.Price;

public interface ProfitRepository extends IncomeSaver, OutcomeSaver {
    Price findIncome();
    Price findOutcome();
}
