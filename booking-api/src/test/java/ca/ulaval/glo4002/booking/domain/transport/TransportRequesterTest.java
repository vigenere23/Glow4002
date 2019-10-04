package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.any;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.ID;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.ShuttlePersistance;

class TransportRequesterTest {

    private List<Shuttle> shuttlesEarth = new LinkedList<Shuttle>();
    private List<Shuttle> shuttlesUlavalogy = new LinkedList<Shuttle>();
    private ShuttlePersistance transportRepository;
    private Shuttle mockedShuttle;
    private TransportRequester transportRequester;
    private Glow4002 festival;
    private final static ID PASS_NUMBER = new ID(1234L);
    private final static LocalDate DATE = LocalDate.of(2050, 7, 18);
    private final static LocalDate OUT_OF_FESTIVAL_DATE = LocalDate.of(2050, 07, 10);

    @BeforeEach
    public void setUp() {
        transportRepository = mock(ShuttlePersistance.class);
        mockedShuttle = mock(SpaceX.class);
        festival = mock(Glow4002.class);
        
        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(true);
        when(festival.getStartDate()).thenReturn(LocalDate.now());
        when(festival.getEndDate()).thenReturn(LocalDate.now());

        transportRequester = new TransportRequester(transportRepository, festival);
        shuttlesEarth.add(mockedShuttle);
        shuttlesUlavalogy.add(mockedShuttle);

        willReturn(shuttlesEarth).given(transportRepository).getShuttles(Location.EARTH);
        willReturn(shuttlesEarth).given(transportRepository).getShuttlesByDate(Location.EARTH, DATE);
        willReturn(shuttlesUlavalogy).given(transportRepository).getShuttles(Location.ULAVALOGY);
        willReturn(shuttlesUlavalogy).given(transportRepository).getShuttlesByDate(Location.ULAVALOGY, DATE);
    }

    @Test
    public void whenGetAllDeparture_thenCallMethodGetShuttlesFromEarthFromRepository() {
        transportRequester.getAllDepartures();
        verify(transportRepository).getShuttles(Location.EARTH);
    }

    @Test
    public void whenGetAllDeparture_thenReturnListOfShuttlesForLocation() {
        assertEquals(shuttlesEarth, transportRequester.getAllDepartures());
    }

    @Test
    public void whenGetAllArrivals_thenCallMethodGetShuttlesFromEarthFromRepository() {
        transportRequester.getAllArrivals();
        verify(transportRepository).getShuttles(Location.ULAVALOGY);
    }

    @Test
    public void whenGetAllArrivals_thenReturnListOfShuttlesForLocation() {
        assertEquals(shuttlesUlavalogy, transportRequester.getAllArrivals());
    }

    @Test
    public void givenDate_whenGetShuttlesDepartureByDate_thenCallMethodGetShuttlesByDateFromRepository() throws OutOfFestivalDatesException {
        transportRequester.getShuttlesDepartureByDate(DATE);
        verify(transportRepository).getShuttlesByDate(Location.EARTH, DATE);
    }
    
    @Test
    public void givenDate_whenGetShuttlesDepartureByDate_thenReturnListOfShuttlesForLocationAndDate() throws OutOfFestivalDatesException {        
        assertEquals(shuttlesEarth, transportRequester.getShuttlesDepartureByDate(DATE));
    }

    @Test
    public void givenDateOutsideOfFestival_whenGetShuttlesDepartureByDate_throwsException() throws OutOfFestivalDatesException {
        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(false);
        assertThrows(OutOfFestivalDatesException.class, () -> transportRequester.getShuttlesDepartureByDate(OUT_OF_FESTIVAL_DATE));
    }

    @Test
    public void givenDate_whenGetShuttlesArrivalByDate_thenCallMethodGetShuttlesByDateFromRepository() throws OutOfFestivalDatesException {
        transportRequester.getShuttlesArrivalByDate(DATE);
        verify(transportRepository).getShuttlesByDate(Location.ULAVALOGY, DATE);
    }
    
    @Test
    public void givenDate_whenGetShuttlesArrivalByDate_thenReturnListOfShuttlesForLocationAndDate() throws OutOfFestivalDatesException {        
        assertEquals(shuttlesUlavalogy, transportRequester.getShuttlesArrivalByDate(DATE));
    }

    @Test
    public void givenDateOutsideOfFestival_whenGetShuttlesArrivalByDate_throwsException() throws OutOfFestivalDatesException {
        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(false);
        assertThrows(OutOfFestivalDatesException.class, () -> transportRequester.getShuttlesArrivalByDate(OUT_OF_FESTIVAL_DATE));
    }
    
    @Test
    public void givenShuttleCategoryPassNumberAndDate_whenReserveDeparture_thenCallMethodGetShuttles() {
        transportRequester.reserveDeparture(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);
        verify(transportRepository).getShuttles(Location.EARTH);
    }
    
    @Test
    public void whenReserveDeparture_thenSaveDepartureIsCalled() {        
        transportRequester.reserveDeparture(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);
        verify(transportRepository).saveDeparture(shuttlesEarth);
    }
    
    @Test
    public void givenShuttleCategoryPassNumberAndDate_whenReserveArrival_thenCallMethodGetShuttles() {        
        transportRequester.reserveArrival(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);
        verify(transportRepository).getShuttles(Location.ULAVALOGY);
    }
    
    @Test
    public void whenReserveDeparture_thenSaveArrivalIsCalled() {        
        transportRequester.reserveArrival(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);
        verify(transportRepository).saveArrival(shuttlesUlavalogy);
    }
}
