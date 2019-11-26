package ca.ulaval.glo4002.booking.application.profit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfitUseCaseTest {
    
    private final static int NUMBER_OF_DECIMAL = 2;
    private final static float ROUNDED_PRICE = 20;

    @Mock ProfitCalculator profitCalculator;
    @Mock ProfitRepository profitRepository;
    @Mock Price profit;
    @Mock Price income;
    @Mock Price outcome;
    @InjectMocks ProfitUseCase profitUseCase;

    @Test
    public void whenGetIncome_thenReturnIncomeRoundedFromRepository() {
        when(profitRepository.findIncome()).thenReturn(income);
        when(income.getRoundedAmount(NUMBER_OF_DECIMAL)).thenReturn(ROUNDED_PRICE);

        double incomeRounded = profitUseCase.getIncome();

        assertEquals(ROUNDED_PRICE, incomeRounded, 2);
    }

    @Test
    public void whenGetOutcome_thenReturnOutcomeRoundedFromRepository() {
        when(profitRepository.findOutcome()).thenReturn(outcome);
        when(outcome.getRoundedAmount(NUMBER_OF_DECIMAL)).thenReturn(ROUNDED_PRICE);

        double outcomeRounded = profitUseCase.getOutcome();

        assertEquals(ROUNDED_PRICE, outcomeRounded, 2);
    }

    @Test
    public void whenGetProfit_thenReturnProfitRoundedFromRepository() {
        mockProfitRepository();
        mockProfitCalculator();
        when(profit.getRoundedAmount(NUMBER_OF_DECIMAL)).thenReturn(ROUNDED_PRICE);

        double profitRounded = profitUseCase.getProfit();

        assertEquals(ROUNDED_PRICE, profitRounded, 2);
    }

    private void mockProfitCalculator() {
        when(profitCalculator.getProfit(income, outcome)).thenReturn(profit);
    }

    private void mockProfitRepository() {
        when(profitRepository.findIncome()).thenReturn(income);
        when(profitRepository.findOutcome()).thenReturn(outcome);
    }
}
