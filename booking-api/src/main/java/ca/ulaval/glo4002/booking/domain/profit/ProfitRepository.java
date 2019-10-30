package ca.ulaval.glo4002.booking.domain.profit;

public interface ProfitRepository {
    
    public float getIncome();
    public float getOutcome();
    public void saveIncome(float income);
    public void saveOutcome(float outcome);
}
