package ca.ulaval.glo4002.booking.domain.transport;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;
import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.SpaceX;

class TransportRequesterTest {

    private final static PassNumber PASS_NUMBER = mock(PassNumber.class);
    private final static LocalDate DATE = LocalDate.of(2050, 7, 18);
    private final static int SOME_PASSENGERS = 1;
    private final static ShuttleCategory SOME_SHUTTLE_CATEGORY = ShuttleCategory.SPACE_X;

    private List<Shuttle> shuttlesEarth = new LinkedList<Shuttle>();
    private List<Shuttle> shuttlesUlavalogy = new LinkedList<Shuttle>();
    private ShuttleRepository shuttleRepository;
    private Shuttle mockedShuttle;
    private TransportReserver transportReserver;

    @BeforeEach
    public void setUpTransportReserver() {
        shuttleRepository = mock(ShuttleRepository.class);
        mockedShuttle = mock(SpaceX.class);
        shuttlesEarth.add(mockedShuttle);
        shuttlesUlavalogy.add(mockedShuttle);

        transportReserver = new TransportReserver(shuttleRepository);        
    }
    
    @Test
    public void givenShuttleCategoryPassNumbeDateAndPassengers_whenReserveDeparture_thenGetShuttlesList() {
        transportReserver.reserveDeparture(SOME_SHUTTLE_CATEGORY, DATE, PASS_NUMBER, SOME_PASSENGERS);
        verify(shuttleRepository).findShuttlesByLocation(Location.EARTH);
    }

    @Test
    public void whenReserveDeparture_thenSaveDepartureIsCalled() {
        when(shuttleRepository.findShuttlesByLocation(Location.EARTH)).thenReturn(shuttlesEarth);
        transportReserver.reserveDeparture(SOME_SHUTTLE_CATEGORY, DATE, PASS_NUMBER, SOME_PASSENGERS);
        
        verify(shuttleRepository).saveDeparture(shuttlesEarth);
    }
    
    @Test
    public void givenShuttleCategoryPassNumberDateAndPassengers_whenReserveArrival_thenCallMethodGetShuttles() {        
        transportReserver.reserveArrival(SOME_SHUTTLE_CATEGORY, DATE, PASS_NUMBER, SOME_PASSENGERS);
        verify(shuttleRepository).findShuttlesByLocation(Location.ULAVALOGY);
    }
    
    @Test
    public void whenReserveArrival_thenSaveArrivalIsCalled() {
        when(shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY)).thenReturn(shuttlesUlavalogy);
        transportReserver.reserveArrival(SOME_SHUTTLE_CATEGORY, DATE, PASS_NUMBER, SOME_PASSENGERS);

        verify(shuttleRepository).saveArrival(shuttlesUlavalogy);
    }
}
