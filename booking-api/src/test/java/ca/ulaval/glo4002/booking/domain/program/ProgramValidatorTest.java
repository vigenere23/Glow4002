package ca.ulaval.glo4002.booking.domain.program;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;

public class ProgramValidatorTest {

    private final static Activity SOME_ACTIVITY = Activity.CARDIO;
    private final static String SOME_ARTIST_NAME = "Lady Gamma";
    private final static LocalDate OUT_OF_FESTIVAL_DATE = LocalDate.of(2050, 7, 12);
    private final static LocalDate DUPLICATE_FESTIVAL_DATE = LocalDate.of(2050, 7, 18);
    private final static LocalDate FESTIVAL_DATE = LocalDate.of(2050, 7, 17);

    private ProgramValidator programValidator;
    private List<SingleDayProgram> singleDaysProgram = new LinkedList<>();
    private FestivalDates glow4002Dates = new Glow4002Dates();
    private SingleDayProgram singleDay;

    @BeforeEach
    public void setUpProgramCreation() {
        programValidator = new ProgramValidator(glow4002Dates);
        createProgram();
    }

    @Test
    public void whenValidateProgram_thenProgramIsCreated() {
        programValidator.validateProgram(singleDaysProgram);
        assertDoesNotThrow(() -> {});
    }

    @Test
    public void givenOutOfFestivalDate_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        singleDay = new SingleDayProgram(SOME_ACTIVITY, SOME_ARTIST_NAME, OUT_OF_FESTIVAL_DATE);
        singleDaysProgram.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDaysProgram));
    }
    
    @Test
    public void givenTwoIdenticalDates_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        singleDay = new SingleDayProgram(SOME_ACTIVITY, SOME_ARTIST_NAME, DUPLICATE_FESTIVAL_DATE);
        singleDaysProgram.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDaysProgram));
    }
  
    @Test
    public void givenTooLongProgram_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        singleDay = new SingleDayProgram(SOME_ACTIVITY, SOME_ARTIST_NAME, FESTIVAL_DATE);
        singleDaysProgram.add(singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDaysProgram));
    }

    @Test
    public void givenSameArtistForTwoDays_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        singleDay = new SingleDayProgram(SOME_ACTIVITY, "Coldray", FESTIVAL_DATE);
        singleDaysProgram.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDaysProgram));
    }

    @Test
    public void givenActivityStringForPm_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        singleDay = new SingleDayProgram(SOME_ACTIVITY, "yoga", FESTIVAL_DATE);
        singleDaysProgram.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDaysProgram));
    }

    private void createProgram() {
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "Lady Gamma", LocalDate.of(2050, 7, 17)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "Sun 41", LocalDate.of(2050, 7, 18)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "Kelvin Harris",  LocalDate.of(2050, 7, 19)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "Bruno Mars",  LocalDate.of(2050, 7, 20)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "Coldray",  LocalDate.of(2050, 7, 21)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "David Glowie",  LocalDate.of(2050, 7, 22)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "30 seconds to Mars",  LocalDate.of(2050, 7, 23)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "Novana",  LocalDate.of(2050, 7, 24)));
    }
}