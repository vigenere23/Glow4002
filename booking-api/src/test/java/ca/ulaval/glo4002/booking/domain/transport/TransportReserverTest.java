package ca.ulaval.glo4002.booking.domain.transport;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;

@ExtendWith(MockitoExtension.class)
class TransportReserverTest {

    private final static PassengerNumber SOME_PASSENGER_NUMBER = mock(PassengerNumber.class);
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 7, 18);
    private final static int SOME_PASSENGERS = 1;
    private final static ShuttleCategory SOME_SHUTTLE_CATEGORY = ShuttleCategory.SPACE_X;

    private List<Shuttle> someShuttles;
    
    @Mock Shuttle mockedShuttle;
    @Mock ShuttleRepository shuttleRepository;
    @Mock ShuttleFiller shuttleFiller;
    @InjectMocks TransportReserver transportReserver;

    @BeforeEach
    public void setUpTransportReserver() {
        someShuttles = new ArrayList<>();

        mockShuttlesList();
        mockShuttleFiller();
    }
    
    @Test
    public void whenReserveDeparture_thenGetShuttlesListFromShuttleRepositoryIsCalled() {
        when(shuttleRepository.findShuttlesByLocation(Location.EARTH)).thenReturn(someShuttles);
        transportReserver.reserveDeparture(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASSENGER_NUMBER, SOME_PASSENGERS);
        verify(shuttleRepository).findShuttlesByLocation(Location.EARTH);
    }

    @Test
    public void whenReserveDeparture_thenSaveDepartureFromRepositoryIsCalled() {
        when(shuttleRepository.findShuttlesByLocation(Location.EARTH)).thenReturn(someShuttles);
        transportReserver.reserveDeparture(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASSENGER_NUMBER, SOME_PASSENGERS);
        verify(shuttleRepository).saveDeparture(someShuttles);
    }

    @Test
    public void whenReserveDeparture_thenFillShuttleFromShuttleFillerIsCalled() {
        when(shuttleRepository.findShuttlesByLocation(Location.EARTH)).thenReturn(someShuttles);
        transportReserver.reserveDeparture(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASSENGER_NUMBER, SOME_PASSENGERS);
        verify(shuttleFiller).fillShuttle(someShuttles, SOME_SHUTTLE_CATEGORY, SOME_PASSENGER_NUMBER, SOME_DATE, SOME_PASSENGERS);
    }
    
    @Test
    public void whenReserveArrival_thenGetShuttlesListFromRepositoryIsCalled() {
        when(shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY)).thenReturn(someShuttles);
        transportReserver.reserveArrival(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASSENGER_NUMBER, SOME_PASSENGERS);
        verify(shuttleRepository).findShuttlesByLocation(Location.ULAVALOGY);
    }
    
    @Test
    public void whenReserveArrival_thenSaveArrivalFromRepositoryIsCalled() {
        when(shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY)).thenReturn(someShuttles);
        transportReserver.reserveArrival(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASSENGER_NUMBER, SOME_PASSENGERS);
        verify(shuttleRepository).saveArrival(someShuttles);
    }

    @Test
    public void whenReserveArrival_thenFillShuttleFromShuttleFillerIsCalled() {
        when(shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY)).thenReturn(someShuttles);
        transportReserver.reserveArrival(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASSENGER_NUMBER, SOME_PASSENGERS);
        verify(shuttleFiller).fillShuttle(someShuttles, SOME_SHUTTLE_CATEGORY, SOME_PASSENGER_NUMBER, SOME_DATE, SOME_PASSENGERS);
    }

    private void mockShuttlesList() {
        someShuttles.add(mockedShuttle);    
    }

    private void mockShuttleFiller() {
        when(shuttleFiller.fillShuttle(someShuttles, SOME_SHUTTLE_CATEGORY, SOME_PASSENGER_NUMBER, SOME_DATE, SOME_PASSENGERS)).thenReturn(someShuttles);
    }
}
