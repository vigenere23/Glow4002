package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

@ExtendWith(MockitoExtension.class)
class ShuttleFillerTest {
    
    private final static LocalDate DATE = LocalDate.of(2050, 7, 22);
    private final static PassengerNumber PASSENGER_NUMBER = mock(PassengerNumber.class);
    private final static int ONE_PLACE = 1;
    private final static int SOME_PLACES = 5;

    private List<Shuttle> shuttles;
    
    @Mock Shuttle firstMockedShuttle;
    @Mock Shuttle secondMockedShuttle;
    @Mock ShuttleFactory shuttleFactory;
    @Mock OutcomeSaver outcomeSaver;
    @InjectMocks ShuttleFiller shuttleFiller;

    @BeforeEach
    public void setUpShuttleFiller() {
        shuttles = new ArrayList<>();
        mockShuttleFactory();
    }

    @Test
    public void givenShuttleListWithoutCategory_whenFillOnePlaceShuttle_thenShuttleOfNewCategoryIsAddedToList() {
        when(firstMockedShuttle.hasCategory(ShuttleCategory.SPACE_X)).thenReturn(false);
        when(firstMockedShuttle.hasDate(DATE)).thenReturn(true);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASSENGER_NUMBER, DATE, ONE_PLACE);
        
        assertEquals(2, shuttles.size());
    }
    
    @Test
    public void givenShuttleListAndDate_whenFillOnePlaceShuttle_thenAddNewShuttleToShuttlesIfAbsentForThatDate() {
        when(firstMockedShuttle.hasDate(DATE)).thenReturn(false);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASSENGER_NUMBER, DATE, ONE_PLACE);
        
        assertEquals(2, shuttles.size());
    }

    @Test
    public void givenShuttleListAndFullShuttle_whenFillOnePlaceShuttle_thenAddNewShuttletoShuttlesIfShuttleForDateIsFull() {
        when(firstMockedShuttle.hasCategory(ShuttleCategory.SPACE_X)).thenReturn(true);
        when(firstMockedShuttle.hasDate(DATE)).thenReturn(true);
        when(firstMockedShuttle.hasAvailableCapacity(ONE_PLACE)).thenReturn(false);
        shuttles.add(firstMockedShuttle);
        
        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASSENGER_NUMBER, DATE, ONE_PLACE);
        
        assertEquals(2, shuttles.size());
    }

    @Test
    public void givenShuttleListWithoutAnyShuttleToFill_whenFillOnePlaceShuttle_saveOutcomeFromOutcomeSaverIsCalled() {
        when(firstMockedShuttle.hasDate(DATE)).thenReturn(false);
        shuttles.add(firstMockedShuttle);
        
        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.SPACE_X, PASSENGER_NUMBER, DATE, SOME_PLACES);
        
        verify(secondMockedShuttle).saveOutcome(outcomeSaver);
    }
    
    @Test
    public void givenShuttleList_whenFillOnePlaceShuttle_thenAddPassNumberToShuttleNotFull() {
        when(firstMockedShuttle.hasAvailableCapacity(ONE_PLACE)).thenReturn(true);
        when(firstMockedShuttle.hasCategory(ShuttleCategory.ET_SPACESHIP)).thenReturn(true);
        when(firstMockedShuttle.hasDate(DATE)).thenReturn(true);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.ET_SPACESHIP, PASSENGER_NUMBER, DATE, ONE_PLACE);

        verify(firstMockedShuttle).addPassenger(PASSENGER_NUMBER);
    }

    @Test
    public void givenShuttleList_whenFillSomePlacesShuttle_thenAddSeveralPassNumberToShuttleNotFull() {
        when(firstMockedShuttle.hasAvailableCapacity(SOME_PLACES)).thenReturn(true);
        when(firstMockedShuttle.hasCategory(ShuttleCategory.MILLENNIUM_FALCON)).thenReturn(true);
        when(firstMockedShuttle.hasDate(DATE)).thenReturn(true);
        shuttles.add(firstMockedShuttle);

        shuttleFiller.fillShuttle(shuttles, ShuttleCategory.MILLENNIUM_FALCON, PASSENGER_NUMBER, DATE, SOME_PLACES);

        verify(firstMockedShuttle, times(SOME_PLACES)).addPassenger(PASSENGER_NUMBER);
    }

    private void mockShuttleFactory() {
        lenient().when(shuttleFactory.createShuttle(ShuttleCategory.SPACE_X, DATE)).thenReturn(secondMockedShuttle);
    }
}
