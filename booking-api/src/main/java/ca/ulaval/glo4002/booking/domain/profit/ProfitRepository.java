package ca.ulaval.glo4002.booking.domain.profit;

import ca.ulaval.glo4002.booking.domain.Price;

public interface ProfitRepository {
    
    public Price findIncome();
    public Price findOutcome();
    public void updateIncome(Price income);
    public void updateOutcome(Price outcome);
}
