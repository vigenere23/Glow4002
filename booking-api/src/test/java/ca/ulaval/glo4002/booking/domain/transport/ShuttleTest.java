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
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class ShuttleTest {

    private final static int ONE_PLACE = 1;
    private final static PassengerNumber PASSENGER_NUMBER = mock(PassengerNumber.class);
	
    private ShuttleImplementationTest shuttle;
    private Price price;
    private OutcomeSaver outcomeSaver;

    private class ShuttleImplementationTest extends Shuttle {

        public ShuttleImplementationTest(LocalDate date, Price price) {
            this.date = date;
            this.price = price;
            capacity = 2;
            category = ShuttleCategory.SPACE_X;
        }

        public List<PassengerNumber> getPassengers() {
                return passengers;
        }  
        
        public void saveOutcome(OutcomeSaver outcomeSaver) {
            outcomeSaver.saveOutcome(price);
        }
    }    

    @BeforeEach
    public void setUp() {
        price = new Price(100);
        outcomeSaver = mock(OutcomeSaver.class);
        shuttle = new ShuttleImplementationTest(LocalDate.of(2050, 7, 17), price);
    }

    @Test
    public void givenPassNumber_whenAddNewPassNumber_thenAPassNumberIsInShuttle() {
        shuttle.addPassenger(PASSENGER_NUMBER);
        assertEquals(PASSENGER_NUMBER, shuttle.getPassengers().get(0));
    }
    
    @Test
    public void givenPartiallyFullShuttle_whenIsFullMethod_thenShuttleHasAvailablePlaceLeft() {
        boolean nonFullShuttle;
        shuttle.addPassenger(PASSENGER_NUMBER); 
        
        nonFullShuttle = shuttle.hasAvailableCapacity(ONE_PLACE);

        assertTrue(nonFullShuttle);
    }

    @Test
    public void givenPassNumberToFillShuttle_whenIsFullMethod_thenShuttleHasNoAvailablePlaceLeft() {
        boolean fullShuttle;
        shuttle.addPassenger(PASSENGER_NUMBER);
        shuttle.addPassenger(PASSENGER_NUMBER);

        fullShuttle = shuttle.hasAvailableCapacity(ONE_PLACE);

        assertFalse(fullShuttle);
    }

    public void givenOutcomeSaver_whenSaveOutcome_thenSaveOutcomeFromOutcomeSaverWithShuttlePriceIsCalled() {
        shuttle.saveOutcome(outcomeSaver);

        verify(outcomeSaver).saveOutcome(price);
    }
}
