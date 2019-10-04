package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShuttleFillerTest {
    
    private ShuttleFiller shuttleFiller;
    private List<Shuttle> shuttles;
    private Shuttle firstMockedShuttle; 
    private final static LocalDate DATE = LocalDate.of(2050, 7, 22);
    private final static Long PASS_NUMBER = 123456L;

    @BeforeEach
    public void setUp() {
        firstMockedShuttle = mock(ETSpaceship.class);
        shuttles = new LinkedList<Shuttle>();
        shuttleFiller = new ShuttleFiller();
    }

    @Test
    public void givenShuttleListAndShuttleCategory_whenFillShuttle_thenCreateNewShuttleIfShuttleForCategoryIsAbsent() {
        when(firstMockedShuttle.getCategory()).thenReturn(ShuttleCategory.ET_SPACESHIP);
        when(firstMockedShuttle.getDate()).thenReturn(DATE);
        when(firstMockedShuttle.isFull()).thenReturn(false);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE);
        
        assertEquals(2, shuttles.size());
    }
    
    @Test
    public void givenShuttleListAndDate_whenFillShuttle_thenCreateNewShuttleIfShuttleForDateIsAbsent() {
        when(firstMockedShuttle.getCategory()).thenReturn(ShuttleCategory.SPACE_X);
        when(firstMockedShuttle.getDate()).thenReturn(LocalDate.of(2050, 7, 24));
        when(firstMockedShuttle.isFull()).thenReturn(false);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE);
        
        assertEquals(2, shuttles.size());
    }

    @Test
    public void givenShuttleListAndFullShuttle_whenFillShuttle_thenCreateNewShuttleIfShuttleForDateIsFull() {
        when(firstMockedShuttle.getCategory()).thenReturn(ShuttleCategory.SPACE_X);
        when(firstMockedShuttle.getDate()).thenReturn(DATE);
        when(firstMockedShuttle.isFull()).thenReturn(true);
        shuttles.add(firstMockedShuttle);
        
        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE);
        
        assertEquals(2, shuttles.size());
    }
    
    @Test
    public void givenShuttleList_whenFillShuttle_thenAddPassNumberToShuttleNotFull() {
        when(firstMockedShuttle.isFull()).thenReturn(false);
        when(firstMockedShuttle.getCategory()).thenReturn(ShuttleCategory.ET_SPACESHIP);
        when(firstMockedShuttle.getDate()).thenReturn(DATE);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.ET_SPACESHIP, PASS_NUMBER, DATE);

        verify(firstMockedShuttle).addPassNumber(PASS_NUMBER);
    }
}
