package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class ShuttleTest {

    private class ShuttleImplementationTest extends Shuttle {

        public ShuttleImplementationTest(LocalDate date) {
            this.date = date;
            capacity = 2;
            category = ShuttleCategory.SPACE_X;
        }

        public List<PassNumber> getPassNumbers() {
                return passNumbers;
        }
    }    

    private final static int ONE_PLACE = 1;
    private final static PassNumber PASS_NUMBER = mock(PassNumber.class);
    private ShuttleImplementationTest shuttle;

    @BeforeEach
    public void setUpShuttle() {
        shuttle = new ShuttleImplementationTest(LocalDate.of(2050, 7, 17));
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
}

