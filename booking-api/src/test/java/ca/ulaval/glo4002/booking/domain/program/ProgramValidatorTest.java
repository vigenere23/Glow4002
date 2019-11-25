package ca.ulaval.glo4002.booking.domain.program;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.dates.Glow4002Dates;

public class ProgramValidatorTest {

    private final static Activity SOME_ACTIVITY = Activity.CARDIO;
    private final static String SOME_ARTIST_NAME = "Lady Gamma";
    private final static LocalDate OUT_OF_FESTIVAL_DATE = LocalDate.of(2050, 7, 12);
    private final static LocalDate DUPLICATE_FESTIVAL_DATE = LocalDate.of(2050, 7, 18);
    private final static LocalDate FESTIVAL_DATE = LocalDate.of(2050, 7, 17);
    
    private FestivalDates festivalDates;
    private ProgramValidator programValidator;
    private List<ProgramDay> singleDayPrograms = new LinkedList<>();
    private ProgramDay singleDay;

    @BeforeEach
    public void setUpProgramCreation() {
        festivalDates = new Glow4002Dates();
        programValidator = new ProgramValidator(festivalDates);
        createProgram();
    }

    @Test
    public void whenValidateProgram_thenProgramIsCreated() {
        programValidator.validateProgram(singleDayPrograms);
        assertDoesNotThrow(() -> {});
    }

    @Test
    public void givenOutOfFestivalDate_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        singleDay = new ProgramDay(SOME_ACTIVITY, SOME_ARTIST_NAME, OUT_OF_FESTIVAL_DATE);
        singleDayPrograms.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDayPrograms));
    }
    
    @Test
    public void givenTwoIdenticalDates_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        singleDay = new ProgramDay(SOME_ACTIVITY, SOME_ARTIST_NAME, DUPLICATE_FESTIVAL_DATE);
        singleDayPrograms.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDayPrograms));
    }
  
    @Test
    public void givenTooLongProgram_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        singleDay = new ProgramDay(SOME_ACTIVITY, SOME_ARTIST_NAME, FESTIVAL_DATE);
        singleDayPrograms.add(singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDayPrograms));
    }

    @Test
    public void givenSameArtistForTwoDays_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        singleDay = new ProgramDay(SOME_ACTIVITY, "Coldray", FESTIVAL_DATE);
        singleDayPrograms.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDayPrograms));
    }

    @Test
    public void givenActivityStringForPm_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        singleDay = new ProgramDay(SOME_ACTIVITY, "yoga", FESTIVAL_DATE);
        singleDayPrograms.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDayPrograms));
    }

    private void createProgram() {
        singleDayPrograms.add(new ProgramDay(Activity.CARDIO, "Lady Gamma", LocalDate.of(2050, 7, 17)));
        singleDayPrograms.add(new ProgramDay(Activity.CARDIO, "Sun 41", LocalDate.of(2050, 7, 18)));
        singleDayPrograms.add(new ProgramDay(Activity.CARDIO, "Kelvin Harris",  LocalDate.of(2050, 7, 19)));
        singleDayPrograms.add(new ProgramDay(Activity.CARDIO, "Bruno Mars",  LocalDate.of(2050, 7, 20)));
        singleDayPrograms.add(new ProgramDay(Activity.CARDIO, "Coldray",  LocalDate.of(2050, 7, 21)));
        singleDayPrograms.add(new ProgramDay(Activity.CARDIO, "David Glowie",  LocalDate.of(2050, 7, 22)));
        singleDayPrograms.add(new ProgramDay(Activity.CARDIO, "30 seconds to Mars",  LocalDate.of(2050, 7, 23)));
        singleDayPrograms.add(new ProgramDay(Activity.CARDIO, "Novana",  LocalDate.of(2050, 7, 24)));
    }
}