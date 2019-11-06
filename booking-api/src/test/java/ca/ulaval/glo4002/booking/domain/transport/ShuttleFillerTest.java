package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

class ShuttleFillerTest {
    
    private final static LocalDate DATE = LocalDate.of(2050, 7, 22);
    private final static PassNumber PASS_NUMBER = mock(PassNumber.class);
    private static final int ONE_PLACE = 1;
    private static final int SOME_PLACES = 5;

    private ShuttleFiller shuttleFiller;
    private List<Shuttle> shuttles;
    private Shuttle firstMockedShuttle; 

    @BeforeEach
    public void setUp() {
        firstMockedShuttle = mock(Shuttle.class);
        shuttles = new LinkedList<Shuttle>();
        shuttleFiller = new ShuttleFiller();
    }

    @Test
    public void givenShuttleList_whenDoesNotContainCategory_thenShuttleCategoryIsCorrectlyCreated() {
        when(firstMockedShuttle.hasCorrectCategory(ShuttleCategory.SPACE_X)).thenReturn(true);
        when(firstMockedShuttle.hasCorrectDate(LocalDate.now())).thenReturn(true);
        when(firstMockedShuttle.availableCapacity(ONE_PLACE)).thenReturn(false);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE, ONE_PLACE);
        
        assertEquals(2, shuttles.size());
    }
    
    @Test
    public void givenShuttleListAndDate_whenFillOnePlaceShuttle_thenCreateNewShuttleIfShuttleForDateIsAbsent() {
        when(firstMockedShuttle.hasCorrectCategory(ShuttleCategory.SPACE_X)).thenReturn(true);
        when(firstMockedShuttle.hasCorrectDate(LocalDate.now())).thenReturn(false);
        when(firstMockedShuttle.availableCapacity(ONE_PLACE)).thenReturn(false);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE, ONE_PLACE);
        
        assertEquals(2, shuttles.size());
    }

    @Test
    public void givenShuttleListAndFullShuttle_whenFillOnePlaceShuttle_thenCreateNewShuttleIfShuttleForDateIsFull() {
        when(firstMockedShuttle.hasCorrectCategory(ShuttleCategory.SPACE_X)).thenReturn(true);
        when(firstMockedShuttle.hasCorrectDate(DATE)).thenReturn(true);
        when(firstMockedShuttle.availableCapacity(ONE_PLACE)).thenReturn(true);
        shuttles.add(firstMockedShuttle);
        
        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE, ONE_PLACE);
        
        assertEquals(2, shuttles.size());
    }
    
    @Test
    public void givenShuttleList_whenFillOnePlaceShuttle_thenAddPassNumberToShuttleNotFull() {
        when(firstMockedShuttle.availableCapacity(ONE_PLACE)).thenReturn(false);
        when(firstMockedShuttle.hasCorrectCategory(ShuttleCategory.ET_SPACESHIP)).thenReturn(true);
        when(firstMockedShuttle.hasCorrectDate(DATE)).thenReturn(true);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.ET_SPACESHIP, PASS_NUMBER, DATE, ONE_PLACE);

        verify(firstMockedShuttle).addPassNumber(PASS_NUMBER);
    }

    @Test
    public void givenShuttleList_whenFillSomePlacesShuttle_thenAddFivePassNumberToShuttleNotFull() {
        when(firstMockedShuttle.availableCapacity(SOME_PLACES)).thenReturn(false);
        when(firstMockedShuttle.hasCorrectCategory(ShuttleCategory.MILLENNIUM_FALCON)).thenReturn(true);
        when(firstMockedShuttle.hasCorrectDate(DATE)).thenReturn(true);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.MILLENNIUM_FALCON, PASS_NUMBER, DATE, SOME_PLACES);

        verify(firstMockedShuttle, times(SOME_PLACES)).addPassNumber(PASS_NUMBER);
    }
}
