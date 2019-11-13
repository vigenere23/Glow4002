package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private Shuttle secondMockedShuttle;
    private ShuttleFactory shuttleFactory;

    @BeforeEach
    public void setUpShuttleFiller() {
        mockShuttles();
        mockShuttleFactory();
        shuttleFiller = new ShuttleFiller(shuttleFactory);
    }

    @Test
    public void givenShuttleList_whenDoesNotContainCategory_thenShuttleOfNewCategoryIsAddedToList() {
        when(firstMockedShuttle.hasCategory(ShuttleCategory.SPACE_X)).thenReturn(false);
        when(firstMockedShuttle.hasDate(DATE)).thenReturn(true);
        when(firstMockedShuttle.hasAvailableCapacity(ONE_PLACE)).thenReturn(true);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE, ONE_PLACE);
        
        assertEquals(2, shuttles.size());
    }
    
    @Test
    public void givenShuttleListAndDate_whenFillOnePlaceShuttle_thenAddNewShuttleToShuttlesIfAbsentForThatDate() {
        when(firstMockedShuttle.hasCategory(ShuttleCategory.SPACE_X)).thenReturn(true);
        when(firstMockedShuttle.hasDate(DATE)).thenReturn(false);
        when(firstMockedShuttle.hasAvailableCapacity(ONE_PLACE)).thenReturn(true);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE, ONE_PLACE);
        
        assertEquals(2, shuttles.size());
    }

    @Test
    public void givenShuttleListAndFullShuttle_whenFillOnePlaceShuttle_thenAddNewShuttletoShuttlesIfShuttleForDateIsFull() {
        when(firstMockedShuttle.hasCategory(ShuttleCategory.SPACE_X)).thenReturn(true);
        when(firstMockedShuttle.hasDate(DATE)).thenReturn(true);
        when(firstMockedShuttle.hasAvailableCapacity(ONE_PLACE)).thenReturn(false);
        shuttles.add(firstMockedShuttle);
        
        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE, ONE_PLACE);
        
        assertEquals(2, shuttles.size());
    }

    @Test
    public void givenShuttleListWithoutAnyShuttleToFill_whenFillOnePlaceShuttle_SaveOutcomeFromShuttleIsCalled() {
        when(firstMockedShuttle.hasCategory(ShuttleCategory.SPACE_X)).thenReturn(false);
        when(firstMockedShuttle.hasDate(DATE)).thenReturn(false);
        when(firstMockedShuttle.hasAvailableCapacity(ONE_PLACE)).thenReturn(false);
        shuttles.add(firstMockedShuttle);
        
        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE, SOME_PLACES);
        
        verify(secondMockedShuttle).saveOutcome();
    }
    
    @Test
    public void givenShuttleList_whenFillOnePlaceShuttle_thenAddPassNumberToShuttleNotFull() {
        when(firstMockedShuttle.hasAvailableCapacity(ONE_PLACE)).thenReturn(true);
        when(firstMockedShuttle.hasCategory(ShuttleCategory.ET_SPACESHIP)).thenReturn(true);
        when(firstMockedShuttle.hasDate(DATE)).thenReturn(true);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.ET_SPACESHIP, PASS_NUMBER, DATE, ONE_PLACE);

        verify(firstMockedShuttle).addPassNumber(PASS_NUMBER);
    }

    @Test
    public void givenShuttleList_whenFillSomePlacesShuttle_thenAddSeveralPassNumberToShuttleNotFull() {
        when(firstMockedShuttle.hasAvailableCapacity(SOME_PLACES)).thenReturn(true);
        when(firstMockedShuttle.hasCategory(ShuttleCategory.MILLENNIUM_FALCON)).thenReturn(true);
        when(firstMockedShuttle.hasDate(DATE)).thenReturn(true);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.MILLENNIUM_FALCON, PASS_NUMBER, DATE, SOME_PLACES);

        verify(firstMockedShuttle, times(SOME_PLACES)).addPassNumber(PASS_NUMBER);
    }

    private void mockShuttles() {
        firstMockedShuttle = mock(Shuttle.class);
        secondMockedShuttle = mock(Shuttle.class);
        shuttles = new ArrayList<>();
    }

    private void mockShuttleFactory() {
        shuttleFactory = mock(ShuttleFactory.class);
        when(shuttleFactory.createShuttle(ShuttleCategory.SPACE_X, DATE)).thenReturn(secondMockedShuttle);
    }
}
