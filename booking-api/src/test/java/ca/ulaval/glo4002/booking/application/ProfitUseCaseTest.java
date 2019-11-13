package ca.ulaval.glo4002.booking.application;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;

class ProfitUseCaseTest {
    
    private final int NUMBER_OF_DECIMAL = 2;
    private final double ROUNDED_PRICE = 20;

    private ProfitUseCase profitUseCase;
    private ProfitCalculator profitCalculator;
    private ProfitRepository profitRepository;
    private Price profit;
    private Price income;
    private Price outcome;

    @BeforeEach
    public void setUp() {
        mockPrices();
        mockProfitRepository();
        mockProfitCalculator();
        profitUseCase = new ProfitUseCase(profitCalculator, profitRepository);
    }

    @Test
    public void whenGetIncome_thenReturnIncomeRoundedFromRepository() {
        when(income.getRoundedAmount(NUMBER_OF_DECIMAL)).thenReturn(ROUNDED_PRICE);

        double incomeRounded = profitUseCase.getIncome();

        assertEquals(ROUNDED_PRICE, incomeRounded, 2);
    }

    @Test
    public void whenGetOutcome_thenReturnOutcomeRoundedFromRepository() {
        when(outcome.getRoundedAmount(NUMBER_OF_DECIMAL)).thenReturn(ROUNDED_PRICE);

        double outcomeRounded = profitUseCase.getOutcome();

        assertEquals(ROUNDED_PRICE, outcomeRounded, 2);
    }

    @Test
    public void whenGetProfit_thenReturnProfitRoundedFromRepository() {
        when(profit.getRoundedAmount(NUMBER_OF_DECIMAL)).thenReturn(ROUNDED_PRICE);

        double profitRounded = profitUseCase.getProfit();

        assertEquals(ROUNDED_PRICE, profitRounded, 2);
    }

    private void mockPrices() {
        profit = mock(Price.class);
        income = mock(Price.class);
        outcome = mock(Price.class);
    }

    private void mockProfitCalculator() {
        profitCalculator = mock(ProfitCalculator.class);
        when(profitCalculator.getProfit(income, outcome)).thenReturn(profit);
    }

    private void mockProfitRepository() {
        profitRepository = mock(ProfitRepository.class);
        when(profitRepository.findIncome()).thenReturn(income);
        when(profitRepository.findOutcome()).thenReturn(outcome);
    }
}