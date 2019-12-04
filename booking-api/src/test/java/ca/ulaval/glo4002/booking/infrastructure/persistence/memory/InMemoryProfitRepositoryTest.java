package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;

class InMemoryProfitRepositoryTest {

    private InMemoryProfitRepository profitRepository;
    
    @BeforeEach
    public void setUp() {
        profitRepository = new InMemoryProfitRepository(); 
    }
    
    @Test
    public void givenZeroTotalIncome_whenAddingIncome_thenTheTotalIncomeIsTheAddedIncome() {
        Price income = new Price(100);
        profitRepository.addIncome(income);
        assertThat(profitRepository.findIncome()).isEqualTo(income);
    }

    @Test
    public void givenZeroTotalOutcome_whenAddingOutcome_thenTheTotalIncomeIsTheAddedOutcome() {
        Price outcome = new Price(100);
        profitRepository.addOutcome(outcome);
        assertThat(profitRepository.findOutcome()).isEqualTo(outcome);
    }

    @Test
    public void givenNonEmptyIncome_whenAddingIncome_thenItAddsToTheTotalIncome() {
        Price income = new Price(100);
        profitRepository.addIncome(income);

        profitRepository.addIncome(income);

        assertThat(profitRepository.findIncome()).isEqualTo(income.plus(income));
    }

    @Test
    public void givenNonEmptyOutcome_whenAddingOutcome_thenItAddsToTheTotalOutcome() {
        Price outcome = new Price(100);
        profitRepository.addOutcome(outcome);

        profitRepository.addOutcome(outcome);

        assertThat(profitRepository.findOutcome()).isEqualTo(outcome.plus(outcome));
    }
}
