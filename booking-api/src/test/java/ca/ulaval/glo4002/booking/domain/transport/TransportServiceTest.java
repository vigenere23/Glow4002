package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.ShuttlePersistance;

class TransportServiceTest {

    private TransportService transportService;
    private List<Shuttle> shuttlesEarth = new LinkedList<Shuttle>();
    private List<Shuttle> shuttlesUlavalogy = new LinkedList<Shuttle>();
    private ShuttlePersistance transportRepository;
    private ShuttleFiller shuttleFiller;
    private Shuttle mockedShuttle;
    private final static Long PASS_NUMBER =  1234L;
    private final static LocalDate DATE = LocalDate.of(2050, 7, 18);

    @BeforeEach
    public void createNewTransportService() throws FullCapacityException {
        transportRepository = mock(ShuttlePersistance.class);
        shuttleFiller = mock(ShuttleFiller.class);
        mockedShuttle = mock(SpaceX.class);
        transportService = new TransportService(transportRepository, shuttleFiller);

        shuttlesEarth.add(mockedShuttle);
        shuttlesUlavalogy.add(mockedShuttle);

        willReturn(shuttlesEarth).given(transportRepository).getShuttles(Location.EARTH);
        willReturn(shuttlesUlavalogy).given(transportRepository).getShuttles(Location.ULAVALOGY);
        willReturn(shuttlesUlavalogy).given(shuttleFiller).fillShuttle(shuttlesUlavalogy, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE);
        willReturn(shuttlesEarth).given(shuttleFiller).fillShuttle(shuttlesEarth, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE);
    }

    @Test
    public void givenLocation_whenGetShuttles_thenCallMethodGetShuttlesFromRepository() {
        transportService.getShuttles(Location.EARTH);
        
        verify(transportRepository).getShuttles(Location.EARTH);
    }
    
    @Test
    public void givenLocation_whenGetShuttles_thenReturnListOfShuttlesForLocation() {        
        assertEquals(shuttlesUlavalogy, transportService.getShuttles(Location.ULAVALOGY));
    }
    
    @Test
    public void givenShuttleCategoryPassNumberAndDate_whenReserveDeparture_thenCallMethodGetShuttles() throws FullCapacityException {
        transportService.reserveDeparture(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);
        
        verify(transportRepository).getShuttles(Location.EARTH);
    }
    
    @Test
    public void givenShuttleCategoryPassNumberAndDate_whenReserveDeparture_thenCallMethodFillShuttleFromShuttleFiller() throws FullCapacityException {
        transportService.reserveDeparture(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);
        
        verify(shuttleFiller).fillShuttle(shuttlesEarth, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE);
    }
    
    @Test
    public void givenShuttleCategoryPassNumberAndDate_whenReserveDeparture_thenCallMethodSaveDepartureFromRepository() throws FullCapacityException {        
        transportService.reserveDeparture(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);
        
        verify(transportRepository).saveDeparture(shuttlesEarth);
    }
    
    @Test
    public void givenShuttleCategoryPassNumberAndDate_whenReserveArrival_thenCallMethodGetShuttles() throws FullCapacityException {        
        transportService.reserveArrival(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);
        
        verify(transportRepository).getShuttles(Location.ULAVALOGY);
    }
    
    @Test
    public void givenShuttleCategoryPassNumberAndDate_whenReserveArrival_thenCallMethodFillShuttleFromShuttleFiller() throws FullCapacityException {        
        transportService.reserveArrival(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);
        
        verify(shuttleFiller).fillShuttle(shuttlesUlavalogy, ShuttleCategory.SPACE_X, PASS_NUMBER, DATE);
    }
    
    @Test
    public void givenShuttleCategoryPassNumberAndDate_whenReserveArrival_thenCallMethodSaveArrivalFromRepository() throws FullCapacityException {        
        transportService.reserveArrival(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);

        verify(transportRepository).saveArrival(shuttlesUlavalogy);
    }
}
