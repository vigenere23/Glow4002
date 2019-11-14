package ca.ulaval.glo4002.booking.domain.profit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;

public class ProfitSaverTest {

    private final static Price ADDED_INCOME = new Price(12345);
    private final static Price INCOME_TO_ADD = new Price(100);
    private final static Price ADDED_OUTCOME = new Price(12345);
    private final static Price OUTCOME_TO_ADD = new Price(100);
    
    private ProfitRepository profitRepository;
    private ProfitSaver profitSaver;
    private Price price;

    @BeforeEach
    public void setUpProfitSaver() {
        profitRepository = mock(ProfitRepository.class);
        price = mock(Price.class);

        profitSaver = new ProfitSaver(profitRepository);
    }

    @Test
    public void givenIncome_whenSaveIncome_thenIncomeAddedToIncomeInRepository() {
        when(profitRepository.findIncome()).thenReturn(price);
        when(price.plus(INCOME_TO_ADD)).thenReturn(ADDED_INCOME);

        profitSaver.saveIncome(INCOME_TO_ADD);

        verify(profitRepository).saveIncome(ADDED_INCOME);
    }

    @Test
    public void givenOutcome_whenSaveOutcome_thenOutcomeAddedToOutcomeInRepository() {
        when(profitRepository.findIncome()).thenReturn(price);
        when(price.plus(OUTCOME_TO_ADD)).thenReturn(ADDED_OUTCOME);

        profitSaver.saveIncome(OUTCOME_TO_ADD);

        verify(profitRepository).saveIncome(ADDED_OUTCOME);
    }
}