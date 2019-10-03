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
        this.capacity = 2;
        this.date = date;
        this.category = ShuttleCategory.SPACE_X;
        }

        public List<Long> getPassNumbers() {
            return passNumbers;    
        }
    }    
    
    private ShuttleImplementationTest shuttle;

    @BeforeEach
    public void createNewShuttle() throws FullCapacityException {
        shuttle = new ShuttleImplementationTest(LocalDate.of(2050, 7, 17));
        shuttle.addPassNumber(new Long(123456789));
    }

    @Test
    public void givenPassNumber_whenAddNewPassNumber_thenAddPassesNumberToList() throws FullCapacityException {
        assertEquals(Long.valueOf(123456789), shuttle.getPassNumbers().get(0));
    }
    
    @Test
    public void givenPassNumber_whenIsFull_thenFalseWhenPassNumberLessThanCapacity() throws FullCapacityException {        
        assertFalse(shuttle.isFull());
    }

    @Test
    public void givenPassNumber_whenIsFull_thenTrueWhenPassNumberLessThanCapacity() throws FullCapacityException {
        shuttle.addPassNumber(new Long(1234));
        
        assertTrue(shuttle.isFull());
    }
}

