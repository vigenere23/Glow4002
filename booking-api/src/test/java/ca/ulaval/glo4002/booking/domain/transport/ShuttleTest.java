package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShuttleTest {

    private class ShuttleImplementationTest extends Shuttle {

        public ShuttleImplementationTest(LocalDate date) {
            this.date = date;
            capacity = 2;
            category = ShuttleCategory.SPACE_X;
        }

        public List<Long> getPassNumbers() {
                return passNumbers;    
        }
    }    
    
    private ShuttleImplementationTest shuttle;
    private final static Long PASS_NUMBER = 123456L;

    @BeforeEach
    public void setUp() {
        shuttle = new ShuttleImplementationTest(LocalDate.of(2050, 7, 17));
        shuttle.addPassNumber(PASS_NUMBER);
    }

    @Test
    public void givenPassNumber_whenAddNewPassNumber_thenAddPassesNumberToList() {
        assertEquals(PASS_NUMBER, shuttle.getPassNumbers().get(0));
    }
    
    @Test
    public void givenPartiallyFullShuttle_whenIsFullMethod_thenReturnFalse() {        
        assertFalse(shuttle.isFull());
    }

    @Test
    public void givenPassNumberToFillShuttle_whenIsFullMethod_thenReturnTrue() {
        shuttle.addPassNumber(PASS_NUMBER);
        assertTrue(shuttle.isFull());
    }
}

