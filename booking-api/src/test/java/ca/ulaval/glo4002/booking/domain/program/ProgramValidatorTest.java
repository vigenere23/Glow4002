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

    private ProgramValidator programValidator;
    private List<SingleDayProgram> singleDaysProgram = new LinkedList<SingleDayProgram>();
    private FestivalDates glow4002Dates = new Glow4002Dates();
    private SingleDayProgram singleDay;

    @BeforeEach
    public void setUpProgramCreation() {
        programValidator = new ProgramValidator(glow4002Dates);
        createProgram();
    }

    @Test
    public void whenValidateProgram_thenValidateProgramCreation() {
        programValidator.validateProgram(singleDaysProgram);
        assertDoesNotThrow(() -> {});
    }

    @Test
    public void givenOutOfFestivalDate_whenValidateProgram_thenValidateProgramIsInvalid() {
        singleDay = new SingleDayProgram(Activity.CARDIO, "Lady Gamma", LocalDate.of(2050, 07, 12));
        singleDaysProgram.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDaysProgram));
    }
    
    @Test
    public void givenTwoIdenticalDates_whenValidateProgram_thenValidateProgramIsInvalid() {
        singleDay = new SingleDayProgram(Activity.CARDIO, "Lady Gamma", LocalDate.of(2050, 07, 18));
        singleDaysProgram.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDaysProgram));
    }
  
    @Test
    public void givenTooLongProgram_whenValidateProgram_thenValidateProgramIsInvalid() {
        singleDay = new SingleDayProgram(Activity.CARDIO, "Lady Gamma", LocalDate.of(2050, 07, 18));
        singleDaysProgram.add(singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDaysProgram));
    }

    @Test
    public void givenSameArtistForTwoDays_whenValidateProgram_thenValidateProgramIsInvalid() {
        singleDay = new SingleDayProgram(Activity.CARDIO, "Coldray", LocalDate.of(2050, 07, 17));
        singleDaysProgram.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programValidator.validateProgram(singleDaysProgram));
    }

    private void createProgram() {
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "Lady Gamma", LocalDate.of(2050, 07, 17)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "Sun 41", LocalDate.of(2050, 07, 18)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "Kelvin Harris",  LocalDate.of(2050, 07, 19)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "Bruno Mars",  LocalDate.of(2050, 07, 20)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "Coldray",  LocalDate.of(2050, 07, 21)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "David Glowie",  LocalDate.of(2050, 07, 22)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "30 seconds to Mars",  LocalDate.of(2050, 07, 23)));
        singleDaysProgram.add(new SingleDayProgram(Activity.CARDIO, "Novana",  LocalDate.of(2050, 07, 24)));
    }
}