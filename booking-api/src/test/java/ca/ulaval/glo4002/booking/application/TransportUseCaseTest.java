package ca.ulaval.glo4002.booking.application;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.transport.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransportUseCaseTest {
    private List<Shuttle> shuttlesEarth = new LinkedList<>();
    private List<Shuttle> shuttlesUlavalogy = new LinkedList<>();
    private Glow4002 festival;
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 7, 18);
    private final static LocalDate OUT_OF_FESTIVAL_DATE = LocalDate.of(2050, 7, 10);
    private ShuttleRepository shuttleRepository;
    private TransportUseCase transportUseCase;

    @BeforeEach
    public void setUp() {
        mockShuttles();
        mockFestival();
        shuttleRepository = mock(ShuttleRepository.class);

        transportUseCase = new TransportUseCase(festival, shuttleRepository);
    }

    @Test
    public void whenGetAllDeparture_thenCallMethodGetShuttlesFromEarthFromRepository() {
        transportUseCase.getAllDepartures();

        verify(shuttleRepository).findShuttlesByLocation(Location.EARTH);
    }

    @Test
    public void whenGetAllDeparture_thenReturnListOfShuttlesForLocation() {
        willReturn(shuttlesEarth).given(shuttleRepository).findShuttlesByLocation(Location.EARTH);
        List<Shuttle> expectedShuttles = transportUseCase.getAllDepartures();

        assertEquals(shuttlesEarth, expectedShuttles);
    }

    @Test
    public void whenGetAllArrivals_thenCallMethodGetShuttlesFromEarthFromRepository() {
        transportUseCase.getAllArrivals();

        verify(shuttleRepository).findShuttlesByLocation(Location.ULAVALOGY);
    }

    @Test
    public void whenGetAllArrivals_thenReturnListOfShuttlesForLocation() {
        willReturn(shuttlesUlavalogy).given(shuttleRepository).findShuttlesByLocation(Location.ULAVALOGY);
        List<Shuttle> expectedShuttles = transportUseCase.getAllArrivals();

        assertEquals(shuttlesUlavalogy, expectedShuttles);
    }

    @Test
    public void givenDate_whenGetShuttlesDepartureByDate_thenCallMethodGetShuttlesByDateFromRepository() throws OutOfFestivalDatesException {
        transportUseCase.getShuttlesDepartureByDate(SOME_DATE);

        verify(shuttleRepository).findShuttlesByDate(Location.EARTH, SOME_DATE);
    }

    @Test
    public void givenDate_whenGetShuttlesDepartureByDate_thenReturnListOfShuttlesForLocationAndDate() throws OutOfFestivalDatesException {
        willReturn(shuttlesEarth).given(shuttleRepository).findShuttlesByDate(Location.EARTH, SOME_DATE);
        List<Shuttle> expectedShuttles = transportUseCase.getShuttlesDepartureByDate(SOME_DATE);

        assertEquals(shuttlesEarth, expectedShuttles);
    }

    @Test
    public void givenDateOutsideOfFestival_whenGetShuttlesDepartureByDate_throwsException() throws OutOfFestivalDatesException {
        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(false);

        assertThrows(OutOfFestivalDatesException.class, () -> transportUseCase.getShuttlesDepartureByDate(OUT_OF_FESTIVAL_DATE));
    }

    @Test
    public void givenDate_whenGetShuttlesArrivalByDate_thenCallMethodGetShuttlesByDateFromRepository() throws OutOfFestivalDatesException {
        transportUseCase.getShuttlesArrivalByDate(SOME_DATE);

        verify(shuttleRepository).findShuttlesByDate(Location.ULAVALOGY, SOME_DATE);
    }

    @Test
    public void givenDate_whenGetShuttlesArrivalByDate_thenReturnListOfShuttlesForLocationAndDate() throws OutOfFestivalDatesException {
        willReturn(shuttlesUlavalogy).given(shuttleRepository).findShuttlesByDate(Location.ULAVALOGY, SOME_DATE);
        List<Shuttle> expectedShuttles = transportUseCase.getShuttlesArrivalByDate(SOME_DATE);

        assertEquals(shuttlesUlavalogy, expectedShuttles);
    }

    @Test
    public void givenDateOutsideOfFestival_whenGetShuttlesArrivalByDate_throwsException() throws OutOfFestivalDatesException {
        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(false);

        assertThrows(OutOfFestivalDatesException.class, () -> transportUseCase.getShuttlesArrivalByDate(OUT_OF_FESTIVAL_DATE));
    }

    private void mockFestival() {
        festival = mock(Glow4002.class);

        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(true);
        when(festival.getStartDate()).thenReturn(LocalDate.now());
        when(festival.getEndDate()).thenReturn(LocalDate.now());
    }

    private void mockShuttles() {
        Shuttle mockedShuttle = mock(SpaceX.class);

        shuttlesEarth.add(mockedShuttle);
        shuttlesUlavalogy.add(mockedShuttle);
    }
}