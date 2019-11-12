package ca.ulaval.glo4002.booking.domain.profit;

import ca.ulaval.glo4002.booking.domain.Price;

public class ProfitCalculator {
    
    public Price getProfit(Price income, Price outcome) {
        return income.minus(outcome);
    }
}