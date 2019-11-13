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

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.passes.PassNumber;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class SingleDayProgramTest {

    private final static Activity SOME_ACTIVITY = Activity.CARDIO;
    private final static String SOME_ARTIST_NAME = "Sun 41";
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 07, 22);
    private static final OxygenGrade OXYGEN_GRADE_PROGRAM = OxygenGrade.E;
    private static final LocalDate PROGRAM_REVEAL_DATE = LocalDate.of(2050, 07, 12);
    private final static ShuttleCategory SHUTTLE_CATEGORY = ShuttleCategory.ET_SPACESHIP;
    private final static int SOME_PASSENGERS = 1;
    private final static int SOME_ATTENDEES = 4;
    private final static int SOME_OXYGEN_QUANTITY = 66;
    List<ArtistProgramInformation> artistsForProgram;
    
    private PassNumber passNumber;
    private OxygenReserver oxygenReserver;
    private TransportReserver transportReserver;
    private SingleDayProgram singleDayProgram;
    private FestivalDates festivalDates;
    private ArtistProgramInformation artistProgramInformation;
    
    @BeforeEach
    public void setUpSingleDayProgram() {
        mockDependency();
        mockArtistProgramInformation();

        singleDayProgram = new SingleDayProgram(SOME_ACTIVITY, SOME_ARTIST_NAME, SOME_DATE);
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
        singleDayProgram.orderShuttle(transportReserver, artistsForProgram);
        verify(transportReserver).reserveDeparture(SHUTTLE_CATEGORY, SOME_DATE, passNumber, SOME_PASSENGERS);
    }

    @Test
    public void whenOrderShuttles_thenTransportReserverOrderArrivalShuttle() {
        singleDayProgram.orderShuttle(transportReserver, artistsForProgram);
        verify(transportReserver).reserveArrival(SHUTTLE_CATEGORY, SOME_DATE, passNumber, SOME_PASSENGERS);
    }

    @Test
    public void whenOrderOxygen_thenOxygenReserverOrderOxygen() {
        singleDayProgram.orderOxygen(oxygenReserver, artistsForProgram, SOME_ATTENDEES);
        verify(oxygenReserver).reserveOxygen(PROGRAM_REVEAL_DATE, OXYGEN_GRADE_PROGRAM, SOME_OXYGEN_QUANTITY);
    }

    private void mockDependency() {
        oxygenReserver = mock(OxygenReserver.class);
        transportReserver = mock(TransportReserver.class);
        festivalDates = mock(Glow4002Dates.class);
        passNumber = mock(PassNumber.class);
        artistProgramInformation = mock(ArtistProgramInformation.class);
    }

    private void mockArtistProgramInformation() {
        artistsForProgram = new ArrayList<>();
        artistsForProgram.add(artistProgramInformation);
        when(artistProgramInformation.getArtistName()).thenReturn(SOME_ARTIST_NAME);
        when(artistProgramInformation.getGroupSize()).thenReturn(SOME_PASSENGERS);
        when(artistProgramInformation.getPassNumber()).thenReturn(passNumber);
    }
}