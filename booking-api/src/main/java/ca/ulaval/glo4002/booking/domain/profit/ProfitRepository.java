package ca.ulaval.glo4002.booking.domain.profit;

import ca.ulaval.glo4002.booking.domain.Price;

public interface ProfitRepository {
    Price findIncome();
    Price findOutcome();
    void updateIncome(Price income);
    void updateOutcome(Price outcome);
}
