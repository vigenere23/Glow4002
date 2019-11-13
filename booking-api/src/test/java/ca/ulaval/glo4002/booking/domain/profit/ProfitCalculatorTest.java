package ca.ulaval.glo4002.booking.domain.profit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;

public class ProfitCalculatorTest {
    
    private static final Price INCOME = new Price(100);
    private static final Price OUTCOME = new Price(50);

    private ProfitCalculator profitCalculator;

    @Test
    public void givenIncomeAndOutcome_whenGetProfit_thenReturnDifferenceBetweenIncomeAndOutcome() {
        profitCalculator = new ProfitCalculator();
        Price expectedProfit = INCOME.minus(OUTCOME);
        Price actualProfit;

        actualProfit = profitCalculator.getProfit(INCOME, OUTCOME);

        assertEquals(expectedProfit, actualProfit);
    }


}