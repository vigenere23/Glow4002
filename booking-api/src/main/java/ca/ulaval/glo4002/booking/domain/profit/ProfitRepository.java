package ca.ulaval.glo4002.booking.domain.profit;

public interface ProfitRepository {
    
    public float findIncome();
    public float findOutcome();
    public void saveIncome(float income);
    public void saveOutcome(float outcome);
}
