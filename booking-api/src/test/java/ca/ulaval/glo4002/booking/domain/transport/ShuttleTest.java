package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.passNumber.PassNumber;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class ShuttleTest {

    private final static int ONE_PLACE = 1;
    private final static PassNumber PASS_NUMBER = mock(PassNumber.class);
	
    private ShuttleImplementationTest shuttle;
    private OutcomeSaver outcomeSaver;
    private Price price;

    private class ShuttleImplementationTest extends Shuttle {

        public ShuttleImplementationTest(LocalDate date, OutcomeSaver outcomeSaver, Price price) {
            this.date = date;
            this.price = price;
            this.outcomeSaver= outcomeSaver;
            capacity = 2;
            category = ShuttleCategory.SPACE_X;
        }

        public List<PassNumber> getPassNumbers() {
                return passNumbers;
        }

        public void saveOutcome() {
            outcomeSaver.saveOutcome(price);
        }    
    }    

    @BeforeEach
    public void setUp() {
        outcomeSaver = mock(OutcomeSaver.class);
        price = new Price(100);
        shuttle = new ShuttleImplementationTest(LocalDate.of(2050, 7, 17), outcomeSaver, price);
    }

    @Test
    public void givenPassNumber_whenAddNewPassNumber_thenAPassNumberIsInShuttle() {
        shuttle.addPassNumber(PASS_NUMBER);
        assertEquals(PASS_NUMBER, shuttle.getPassNumbers().get(0));
    }
    
    @Test
    public void givenPartiallyFullShuttle_whenIsFullMethod_thenShuttleHasAvailablePlaceLeft() {
        boolean nonFullShuttle;
        shuttle.addPassNumber(PASS_NUMBER); 
        
        nonFullShuttle = shuttle.hasAvailableCapacity(ONE_PLACE);

        assertTrue(nonFullShuttle);
    }

    @Test
    public void givenPassNumberToFillShuttle_whenIsFullMethod_thenShuttleHasNoAvailablePlaceLeft() {
        boolean fullShuttle;
        shuttle.addPassNumber(PASS_NUMBER);
        shuttle.addPassNumber(PASS_NUMBER);

        fullShuttle = shuttle.hasAvailableCapacity(ONE_PLACE);

        assertFalse(fullShuttle);
    }

    @Test
    public void whenSaveOutcome_thenSaveOutcomeFromOutcomeSaverIsCalled() {
        shuttle.saveOutcome();
        verify(outcomeSaver).saveOutcome(price);
    }
}

