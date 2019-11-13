package ca.ulaval.glo4002.booking.domain.transport;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;
import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.SpaceX;

class TransportReserverTest {

    private final static PassNumber SOME_PASS_NUMBER = mock(PassNumber.class);
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 7, 18);
    private final static int SOME_PASSENGERS = 1;
    private final static ShuttleCategory SOME_SHUTTLE_CATEGORY = ShuttleCategory.SPACE_X;

    private ShuttleRepository shuttleRepository;
    private Shuttle mockedShuttle;
    private List<Shuttle> someShuttles = new ArrayList<>();
    private TransportReserver transportReserver;
    private ShuttleFiller shuttleFiller;

    @BeforeEach
    public void setUpTransportReserver() {
        mockShuttlesList();
        mockShuttleRepository();
        mockShuttleFiller();


        transportReserver = new TransportReserver(shuttleRepository, shuttleFiller);
    }
    
    @Test
    public void whenReserveDeparture_thenGetShuttlesListFromShuttleRepositoryIsCalled() {
        transportReserver.reserveDeparture(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASS_NUMBER, SOME_PASSENGERS);
        verify(shuttleRepository).findShuttlesByLocation(Location.EARTH);
    }

    @Test
    public void whenReserveDeparture_thenSaveDepartureFromRepositoryIsCalled() {
        transportReserver.reserveDeparture(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASS_NUMBER, SOME_PASSENGERS);
        verify(shuttleRepository).saveDeparture(someShuttles);
    }

    @Test
    public void whenReserveDeparture_thenFillShuttleFromShuttleFillerIsCalled() {
        transportReserver.reserveDeparture(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASS_NUMBER, SOME_PASSENGERS);
        verify(shuttleFiller).fillShuttle(someShuttles, SOME_SHUTTLE_CATEGORY, SOME_PASS_NUMBER, SOME_DATE, SOME_PASSENGERS);
    }
    
    @Test
    public void whenReserveArrival_thenGetShuttlesListFromRepositoryIsCalled() {        
        transportReserver.reserveArrival(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASS_NUMBER, SOME_PASSENGERS);
        verify(shuttleRepository).findShuttlesByLocation(Location.ULAVALOGY);
    }
    
    @Test
    public void whenReserveArrival_thenSaveArrivalFromRepositoryIsCalled() {
        transportReserver.reserveArrival(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASS_NUMBER, SOME_PASSENGERS);
        verify(shuttleRepository).saveArrival(someShuttles);
    }

    @Test
    public void whenReserveArrival_thenFillShuttleFromShuttleFillerIsCalled() {
        transportReserver.reserveArrival(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASS_NUMBER, SOME_PASSENGERS);
        verify(shuttleFiller).fillShuttle(someShuttles, SOME_SHUTTLE_CATEGORY, SOME_PASS_NUMBER, SOME_DATE, SOME_PASSENGERS);
    }

    private void mockShuttlesList() {
        mockedShuttle = mock(SpaceX.class);
        someShuttles.add(mockedShuttle);    
    }

    private void mockShuttleRepository() {
        shuttleRepository = mock(ShuttleRepository.class);
        when(shuttleRepository.findShuttlesByLocation(Location.EARTH)).thenReturn(someShuttles);
        when(shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY)).thenReturn(someShuttles);
    } 

    private void mockShuttleFiller() {
        shuttleFiller = mock(ShuttleFiller.class);
        when(shuttleFiller.fillShuttle(someShuttles, SOME_SHUTTLE_CATEGORY, SOME_PASS_NUMBER, SOME_DATE, SOME_PASSENGERS)).thenReturn(someShuttles);
    }
}
