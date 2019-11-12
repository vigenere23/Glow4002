package ca.ulaval.glo4002.booking.api.dtos.profit;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ProfitResponse {
    public final double in;
    public final double out;
    public final double profit;
     
    @JsonCreator
    public ProfitResponse(double income, double outcome, double profit) {
        this.in = income;
        this.out = outcome;
        this.profit = profit;
    }
}
