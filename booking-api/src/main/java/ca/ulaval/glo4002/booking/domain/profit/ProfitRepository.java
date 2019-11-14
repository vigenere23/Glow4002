package ca.ulaval.glo4002.booking.domain.profit;

import ca.ulaval.glo4002.booking.domain.Price;

public interface ProfitRepository {
    
    public Price findIncome();
    public Price findOutcome();
    public void saveIncome(Price income);
    public void saveOutcome(Price outcome);
}
