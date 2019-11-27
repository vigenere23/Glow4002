package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;

class InMemoryProfitRepositoryTest {

    private InMemoryProfitRepository heapProfitRepository;
    
    @BeforeEach
    public void setUp() {
        heapProfitRepository = new InMemoryProfitRepository(); 
    }
    
    @Test
    public void givenIncome_whenSaveIncome_thenReplacesIncome() {
        Price income = new Price(100);
        heapProfitRepository.updateIncome(income);
        assertEquals(income, heapProfitRepository.findIncome());
    }

    @Test
    public void givenOutcome_whenSaveOutcome_thenReplacesOutcome() {
        Price outcome = new Price(100);
        heapProfitRepository.updateOutcome(outcome);
        assertEquals(outcome, heapProfitRepository.findOutcome());
    }
}