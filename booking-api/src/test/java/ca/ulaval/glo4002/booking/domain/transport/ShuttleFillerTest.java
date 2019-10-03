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
    private LocalDate date = LocalDate.of(2050, 7, 22);;
    private Shuttle firstMockedShuttle;

    @BeforeEach
    public void createNewShuttleFiller() {
        firstMockedShuttle = mock(ETSpaceship.class);
        shuttles = new LinkedList<Shuttle>();
        shuttleFiller = new ShuttleFiller();
    }

    @Test
    public void givenShuttleListAndShuttleCategory_whenFillShuttle_thenCreateNewShuttleIfShuttleForCategoryIsAbsent() throws FullCapacityException {
        when(firstMockedShuttle.getCategory()).thenReturn(ShuttleCategory.ET_SPACESHIP);
        when(firstMockedShuttle.getDate()).thenReturn(date);
        when(firstMockedShuttle.isFull()).thenReturn(false);

        shuttles.add(firstMockedShuttle);
        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, 123456L, date);
        
        assertEquals(2, shuttles.size());
    }
    
    @Test
    public void givenShuttleListAndDate_whenFillShuttle_thenCreateNewShuttleIfShuttleForDateIsAbsent() throws FullCapacityException {
        when(firstMockedShuttle.getCategory()).thenReturn(ShuttleCategory.SPACE_X);
        when(firstMockedShuttle.getDate()).thenReturn(LocalDate.of(2050, 7, 24));
        when(firstMockedShuttle.isFull()).thenReturn(false);

        shuttles.add(firstMockedShuttle);
        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, 123456L, date);
        
        assertEquals(2, shuttles.size());
    }

    @Test
    public void givenShuttleListAndFullShuttle_whenFillShuttle_thenCreateNewShuttleIfShuttleForDateIsFull() throws FullCapacityException {
        when(firstMockedShuttle.getCategory()).thenReturn(ShuttleCategory.SPACE_X);
        when(firstMockedShuttle.getDate()).thenReturn(date);
        when(firstMockedShuttle.isFull()).thenReturn(true);

        shuttles.add(firstMockedShuttle);
        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, 123456L, date);
        
        assertEquals(2, shuttles.size());
    }
    
    @Test
    public void givenShuttleList_whenFillShuttle_thenAddPassNumberToShuttleNotFull() throws FullCapacityException {
        when(firstMockedShuttle.isFull()).thenReturn(false);
        when(firstMockedShuttle.getCategory()).thenReturn(ShuttleCategory.ET_SPACESHIP);
        when(firstMockedShuttle.getDate()).thenReturn(date);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.ET_SPACESHIP, 123456L, date);

        verify(firstMockedShuttle).addPassNumber(123456L);
    }
}
