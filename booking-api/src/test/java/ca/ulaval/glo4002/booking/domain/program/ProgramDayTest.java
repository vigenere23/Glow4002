package ca.ulaval.glo4002.booking.domain.program;

import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleCategory;

@ExtendWith(MockitoExtension.class)
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
    private final static int ATTENDEES_OXYGEN_QUANTITY = 60;
    private final static int ARTIST_OXYGEN_QUANTITY = 60;
    private final static Price A_PRICE = new Price(1234.5678);
    
    private List<Artist> artistsForProgram;   
    private ProgramDay singleDayProgram;

    @Mock OxygenRequester oxygenRequester;
    @Mock TransportReserver transportReserver;
    @Mock OutcomeSaver outcomeSaver;
    @Mock Artist artist;
    
    @BeforeEach
    public void setUpSingleDayProgram() {
        mockArtistProgramInformation();
        Artist artist = new Artist(SOME_ARTIST_NAME, A_PRICE, SOME_PASSENGER_NUMBER);
        singleDayProgram = new ProgramDay(SOME_ACTIVITY, artist, SOME_DATE, SOME_ATTENDEES);
    }

    @Test
    public void whenOrderShuttles_thenTransportReserverOrderDepartureShuttle() {
        singleDayProgram.orderShuttle(transportReserver);
        verify(transportReserver).reserveDepartures(SHUTTLE_CATEGORY, SOME_DATE, SOME_PASSENGER_NUMBER, SOME_PASSENGERS);
    }

    @Test
    public void whenOrderShuttles_thenTransportReserverOrderArrivalShuttle() {
        singleDayProgram.orderShuttle(transportReserver);
        verify(transportReserver).reserveArrivals(SHUTTLE_CATEGORY, SOME_DATE, SOME_PASSENGER_NUMBER, SOME_PASSENGERS);
    }

    @Test
    public void whenOrderOxygen_thenOxygenReserverOrderOxygen() {
        singleDayProgram.orderOxygen(oxygenRequester);
        verify(oxygenRequester).requestOxygen(PROGRAM_REVEAL_DATE, OXYGEN_GRADE_PROGRAM, ATTENDEES_OXYGEN_QUANTITY);
        verify(oxygenRequester).requestOxygen(PROGRAM_REVEAL_DATE, OXYGEN_GRADE_PROGRAM, ARTIST_OXYGEN_QUANTITY);
    }

    @Test
    public void whenSaveOutcome_thenOutcomeAddedToOutcomeInRepository() {
        singleDayProgram.saveOutcome(outcomeSaver);
        verify(outcomeSaver).addOutcome(A_PRICE);
    }

    private void mockArtistProgramInformation() {
        artistsForProgram = new ArrayList<>();
        artistsForProgram.add(artist);
    }
}
