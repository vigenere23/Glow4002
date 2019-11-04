package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

class TransportRequesterTest {

    private final static PassNumber PASS_NUMBER = mock(PassNumber.class);
    private final static LocalDate DATE = LocalDate.of(2050, 7, 18);
    private final static LocalDate OUT_OF_FESTIVAL_DATE = LocalDate.of(2050, 07, 10);

    private List<Shuttle> shuttlesEarth = new LinkedList<Shuttle>();
    private List<Shuttle> shuttlesUlavalogy = new LinkedList<Shuttle>();
    private ShuttleRepository shuttleRepository;
    private Shuttle mockedShuttle;
    private TransportRequester transportRequester;
    private Glow4002Dates festival;

    @BeforeEach
    public void setUp() {

        //À retravailler... pourrait probablement être diminuer encore, mais difficilement faisable. 
        shuttleRepository = mock(ShuttleRepository.class);
        mockedShuttle = mock(SpaceX.class);
        festival = mock(Glow4002Dates.class);
        transportRequester = new TransportRequester(shuttleRepository, festival);
        shuttlesEarth.add(mockedShuttle);
        shuttlesUlavalogy.add(mockedShuttle);

        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(true);
        when(festival.getStartDate()).thenReturn(LocalDate.now());
        when(festival.getEndDate()).thenReturn(LocalDate.now());
    }

    @Test
    public void whenGetAllDeparture_thenCallMethodGetShuttlesFromEarthFromRepository() {
        transportRequester.getAllDepartures();
        verify(shuttleRepository).findShuttlesByLocation(Location.EARTH);
    }

    @Test
    public void whenGetAllDeparture_thenReturnListOfShuttlesForLocation() {
        when(shuttleRepository.findShuttlesByLocation(Location.EARTH)).thenReturn(shuttlesEarth);
        assertEquals(shuttlesEarth, transportRequester.getAllDepartures());
    }

    @Test
    public void whenGetAllArrivals_thenCallMethodGetShuttlesFromEarthFromRepository() {
        transportRequester.getAllArrivals();
        verify(shuttleRepository).findShuttlesByLocation(Location.ULAVALOGY);
    }

    @Test
    public void whenGetAllArrivals_thenReturnListOfShuttlesForLocation() {
        when(shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY)).thenReturn(shuttlesUlavalogy);
        assertEquals(shuttlesUlavalogy, transportRequester.getAllArrivals());
    }

    @Test
    public void givenDate_whenGetShuttlesDepartureByDate_thenCallMethodGetShuttlesByDateFromRepository() throws OutOfFestivalDatesException {
        transportRequester.getShuttlesDepartureByDate(DATE);
        verify(shuttleRepository).findShuttlesByDate(Location.EARTH, DATE);
    }
    
    @Test
    public void givenDate_whenGetShuttlesDepartureByDate_thenReturnListOfShuttlesForLocationAndDate() throws OutOfFestivalDatesException { 
        when(shuttleRepository.findShuttlesByDate(Location.EARTH, DATE)).thenReturn(shuttlesEarth);
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
        verify(shuttleRepository).findShuttlesByDate(Location.ULAVALOGY, DATE);
    }
    
    @Test
    public void givenDate_whenGetShuttlesArrivalByDate_thenReturnListOfShuttlesForLocationAndDate() throws OutOfFestivalDatesException {      
        when(shuttleRepository.findShuttlesByDate(Location.ULAVALOGY, DATE)).thenReturn(shuttlesUlavalogy);
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
        verify(shuttleRepository).findShuttlesByLocation(Location.EARTH);
    }
    
    @Test
    public void whenReserveDeparture_thenSaveDepartureIsCalled() {
        when(shuttleRepository.findShuttlesByLocation(Location.EARTH)).thenReturn(shuttlesEarth);

        transportRequester.reserveDeparture(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);
        
        verify(shuttleRepository).saveDeparture(shuttlesEarth);
    }
    
    @Test
    public void givenShuttleCategoryPassNumberAndDate_whenReserveArrival_thenCallMethodGetShuttles() {        
        transportRequester.reserveArrival(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);
        verify(shuttleRepository).findShuttlesByLocation(Location.ULAVALOGY);
    }
    
    @Test
    public void whenReserveArrival_thenSaveArrivalIsCalled() {
        when(shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY)).thenReturn(shuttlesUlavalogy);
        
        transportRequester.reserveArrival(ShuttleCategory.SPACE_X, DATE, PASS_NUMBER);

        verify(shuttleRepository).saveArrival(shuttlesUlavalogy);
    }
}
