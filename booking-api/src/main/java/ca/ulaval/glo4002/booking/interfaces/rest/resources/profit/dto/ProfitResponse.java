package ca.ulaval.glo4002.booking.interfaces.rest.resources.profit.dto;

import ca.ulaval.glo4002.booking.domain.Price;

public class ProfitResponse {

    public final float in;
    public final float out;
    public final float profit;
     
    public ProfitResponse(Price income, Price outcome, Price profit) {
        this.in = income.getRoundedAmountFromCurrencyScale();
        this.out = outcome.getRoundedAmountFromCurrencyScale();
        this.profit = profit.getRoundedAmountFromCurrencyScale();
    }
}
