package ca.ulaval.glo4002.booking.domain.program;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Matchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class ProgramDayTest {

    private final static Activity SOME_ACTIVITY = Activity.CARDIO;
    private final static String SOME_ARTIST_NAME = "Sun 41";
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 7, 22);
    private final static OxygenGrade OXYGEN_GRADE_PROGRAM = OxygenGrade.E;
    private final static LocalDate PROGRAM_REVEAL_DATE = LocalDate.of(2050, 7, 12);
    private final static ShuttleCategory SHUTTLE_CATEGORY = ShuttleCategory.ET_SPACESHIP;
    private final static PassengerNumber SOME_PASSENGER_NUMBER = new PassengerNumber(0);
    private final static int SOME_PASSENGERS = 1;
    private final static int SOME_ATTENDEES = 4;
    private final static int SOME_OXYGEN_QUANTITY = 66;
    
    private List<Artist> artistsForProgram;   
    private OxygenRequester oxygenRequester;
    private TransportReserver transportReserver;
    private ProgramDay singleDayProgram;
    private FestivalDates festivalDates;
    private Artist artist;
    private OutcomeSaver outcomeSaver;
    private Price price;
    
    @BeforeEach
    public void setUpSingleDayProgram() {
        mockDependency();
        mockArtistProgramInformation();
        Artist artist = new Artist(SOME_ARTIST_NAME, price, SOME_PASSENGER_NUMBER);
        singleDayProgram = new ProgramDay(SOME_ACTIVITY, artist, SOME_DATE);
    }

    @Test
    public void whenIsDuringFestivalDate_thenFestivalValidatesDate() {
        singleDayProgram.isDuringFestivalDate(festivalDates);
        verify(festivalDates).isDuringEventTime(any(LocalDate.class));
    }

    @Test
    public void givenValidDate_whenIsDuringFestivalDate_thenIndicatesThatDateIsDuringFestival() {
        when(festivalDates.isDuringEventTime(SOME_DATE)).thenReturn(true);
        boolean validDate = singleDayProgram.isDuringFestivalDate(festivalDates);
        assertTrue(validDate);
    }

    @Test
    public void givenInvalidDate_whenIsDuringFestivalDate_thenIndicatesThatDateIsNotInFestival() {
        when(festivalDates.isDuringEventTime(SOME_DATE)).thenReturn(false);
        boolean validDate = singleDayProgram.isDuringFestivalDate(festivalDates);
        assertFalse(validDate);
    }

    @Test
    public void whenOrderShuttles_thenTransportReserverOrderDepartureShuttle() {
        singleDayProgram.orderShuttle(transportReserver);
        verify(transportReserver).reserveDeparture(SHUTTLE_CATEGORY, SOME_DATE, SOME_PASSENGER_NUMBER, SOME_PASSENGERS);
    }

    @Test
    public void whenOrderShuttles_thenTransportReserverOrderArrivalShuttle() {
        singleDayProgram.orderShuttle(transportReserver);
        verify(transportReserver).reserveArrival(SHUTTLE_CATEGORY, SOME_DATE, SOME_PASSENGER_NUMBER, SOME_PASSENGERS);
    }

    @Test
    public void whenOrderOxygen_thenOxygenReserverOrderOxygen() {
        singleDayProgram.orderOxygen(oxygenRequester, SOME_ATTENDEES);
        verify(oxygenRequester).requestOxygen(PROGRAM_REVEAL_DATE, OXYGEN_GRADE_PROGRAM, SOME_OXYGEN_QUANTITY);
    }

    @Test
    public void whenSaveOutcome_thenOutcomeAddedToOutcomeInRepository() {
        singleDayProgram.saveOutcome(outcomeSaver);
        verify(outcomeSaver).saveOutcome(price);
    }

    private void mockDependency() {
        oxygenRequester = mock(OxygenRequester.class);
        transportReserver = mock(TransportReserver.class);
        festivalDates = mock(FestivalDates.class);
        artist = mock(Artist.class);
        outcomeSaver = mock(OutcomeSaver.class);
        price = mock(Price.class);
    }

    private void mockArtistProgramInformation() {
        artistsForProgram = new ArrayList<>();
        artistsForProgram.add(artist);
        when(artist.getName()).thenReturn(SOME_ARTIST_NAME);
        when(artist.getGroupSize()).thenReturn(SOME_PASSENGERS);
        when(artist.getPassengerNumber()).thenReturn(SOME_PASSENGER_NUMBER);
        when(artist.getPrice()).thenReturn(price);
    }
}
