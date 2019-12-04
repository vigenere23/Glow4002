package ca.ulaval.glo4002.booking.domain.program;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.dates.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.program.exceptions.InvalidProgramException;

public class ProgramValidatorTest {

    private final static Activity SOME_ACTIVITY = Activity.CARDIO;
    private final static String SOME_ACTIVITY_NAME = SOME_ACTIVITY.toString();
    private final static String SOME_ARTIST_NAME = "Lady Gamma";
    private final static int SOME_ATTENDEES = 10;
    private final static LocalDate FESTIVAL_START = LocalDate.of(2050, 7, 13);
    private final static LocalDate FESTIVAL_END = LocalDate.of(2050, 7, 14);
    private final static LocalDate OUT_OF_FESTIVAL_DATE = FESTIVAL_START.minusDays(1);
    
    private Artist artist;
    private Artist artist1;
    private Artist artist2;
    private FestivalDates festivalDates;
    private ProgramValidator programValidator;
    private List<ProgramDay> singleDayPrograms = new LinkedList<>();
    private ProgramDay singleDay;

    @BeforeEach
    public void setUpProgramCreation() {
        artist = mock(Artist.class);
        artist1 = mock(Artist.class);
        artist2 = mock(Artist.class);
        festivalDates = new Glow4002Dates(FESTIVAL_START, FESTIVAL_END);
        programValidator = new ProgramValidator(festivalDates);
        createProgram();
    }

    @Test
    public void whenValidatingValidProgram_thenProgramIsCreatedWithoutError() {
        assertDoesNotThrow(() -> programValidator.validateProgramDays(singleDayPrograms));
    }

    @Test
    public void givenOutOfFestivalDate_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        when(artist.getName()).thenReturn(SOME_ARTIST_NAME);
        singleDay = new ProgramDay(SOME_ACTIVITY, artist, OUT_OF_FESTIVAL_DATE, SOME_ATTENDEES);
        singleDayPrograms.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgramDays(singleDayPrograms));
    }
    
    @Test
    public void givenTwoIdenticalDates_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        when(artist.getName()).thenReturn(SOME_ARTIST_NAME);
        singleDay = new ProgramDay(SOME_ACTIVITY, artist, FESTIVAL_END, SOME_ATTENDEES);
        singleDayPrograms.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgramDays(singleDayPrograms));
    }
  
    @Test
    public void givenTooLongProgram_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        when(artist.getName()).thenReturn(SOME_ARTIST_NAME);
        singleDay = new ProgramDay(SOME_ACTIVITY, artist, FESTIVAL_START, SOME_ATTENDEES);
        singleDayPrograms.add(singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgramDays(singleDayPrograms));
    }

    @Test
    public void givenSameArtistForTwoDays_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        singleDay = new ProgramDay(SOME_ACTIVITY, artist2, FESTIVAL_START, SOME_ATTENDEES);
        singleDayPrograms.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgramDays(singleDayPrograms));
    }

    @Test
    public void givenActivityStringForPm_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        when(artist.getName()).thenReturn(SOME_ACTIVITY_NAME);
        singleDay = new ProgramDay(SOME_ACTIVITY, artist, FESTIVAL_START, SOME_ATTENDEES);
        singleDayPrograms.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgramDays(singleDayPrograms));
    }

    private void createProgram() {
        when(artist1.getName()).thenReturn("Lady Gamma");
        when(artist2.getName()).thenReturn("Sun 41");
        singleDayPrograms.add(new ProgramDay(Activity.CARDIO, artist1, FESTIVAL_START, SOME_ATTENDEES));
        singleDayPrograms.add(new ProgramDay(Activity.CARDIO, artist2, FESTIVAL_END, SOME_ATTENDEES));
    }
}
