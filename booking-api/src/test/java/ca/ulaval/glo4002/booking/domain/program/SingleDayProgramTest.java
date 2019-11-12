package ca.ulaval.glo4002.booking.domain.program;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Matchers.any;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.passes.PassNumber;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class SingleDayProgramTest {

    private final static Activity SOME_ACTIVITY = Activity.CARDIO;
    private final static String SOME_ARTIST_NAME = "Sun 41";
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 07, 22);
    private final static ShuttleCategory SHUTTLE_CATEGORY = ShuttleCategory.ET_SPACESHIP;
    private final static int SOME_PASSENGERS = 1;
    
    private PassNumber passNumber;
    private ArtistRepository artistRepository;
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

    private void mockArtistProgramInformation() {
        when(artistRepository.getArtistByName(SOME_ARTIST_NAME)).thenReturn(artistProgramInformation);
        when(artistProgramInformation.getGroupSize()).thenReturn(SOME_PASSENGERS);
        when(artistProgramInformation.getPassNumber()).thenReturn(passNumber);
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
        singleDayProgram.orderShuttle(transportReserver, artistRepository);
        verify(transportReserver).reserveDeparture(SHUTTLE_CATEGORY, SOME_DATE, passNumber, SOME_PASSENGERS);
    }

    @Test
    public void whenOrderShuttles_thenTransportReserverOrderArrivalShuttle() {
        singleDayProgram.orderShuttle(transportReserver, artistRepository);
        verify(transportReserver).reserveArrival(SHUTTLE_CATEGORY, SOME_DATE, passNumber, SOME_PASSENGERS);
    }

    private void mockDependency() {
        artistRepository = mock(ArtistRepository.class);
        oxygenReserver = mock(OxygenReserver.class);
        transportReserver = mock(TransportReserver.class);
        festivalDates = mock(Glow4002Dates.class);
        passNumber = mock(PassNumber.class);
        artistProgramInformation = mock(ArtistProgramInformation.class);
    }
}