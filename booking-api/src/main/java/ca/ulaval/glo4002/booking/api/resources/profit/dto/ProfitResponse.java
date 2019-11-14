package ca.ulaval.glo4002.booking.api.resources.profit.dto;

public class ProfitResponse {
    public final float in;
    public final float out;
    public final float profit;
     
    public ProfitResponse(float income, float outcome, float profit) {
        this.in = income;
        this.out = outcome;
        this.profit = profit;
    }
}
