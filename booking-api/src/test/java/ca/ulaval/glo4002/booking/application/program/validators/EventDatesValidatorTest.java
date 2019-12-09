package ca.ulaval.glo4002.booking.application.program.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.program.Activity;
import ca.ulaval.glo4002.booking.domain.program.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramDayRequest;

@ExtendWith(MockitoExtension.class)
public class EventDatesValidatorTest {

    private final static String SOME_ACTIVITY_NAME = Activity.CARDIO.toString();
    private final static String SOME_ARTIST_NAME = "Lord Gasper";
    private final static LocalDate A_DATE = LocalDate.now();
    private final static LocalDate ANOTHER_DATE = A_DATE.plusDays(1);
    private final static LocalDate OUT_OF_FESTIVAL_DATE = ANOTHER_DATE.plusDays(1);
    
    private List<ProgramDayRequest> programDayRequests;

    @Mock FestivalDates festivalDates;
    @InjectMocks EventDatesValidator eventDatesValidator;

    @BeforeEach
    public void setUpProgramRequests() {
        programDayRequests = new ArrayList<>();
        programDayRequests.add(new ProgramDayRequest(SOME_ACTIVITY_NAME, SOME_ARTIST_NAME, A_DATE.toString()));
    }

    @Test
    public void givenValidProgramDayRequests_whenValidating_thenProgramIsCreatedWithoutError() {
        programDayRequests.add(new ProgramDayRequest(SOME_ACTIVITY_NAME, SOME_ARTIST_NAME, A_DATE.toString()));
        programDayRequests.add(new ProgramDayRequest(SOME_ACTIVITY_NAME, SOME_ARTIST_NAME, ANOTHER_DATE.toString()));
        freezeFestivalDatesFromCurrentProgramDayRequests();
        
        assertDoesNotThrow(() -> eventDatesValidator.validate(programDayRequests));
    }

    @Test
    public void givenOutOfFestivalDate_whenValidating_thenAnInvalidProgramExceptionIsThrown() {
        programDayRequests.add(new ProgramDayRequest(SOME_ACTIVITY_NAME, SOME_ARTIST_NAME, OUT_OF_FESTIVAL_DATE.toString()));
        when(festivalDates.getFestivalDays()).thenReturn(Arrays.asList(A_DATE, ANOTHER_DATE));

        assertThrows(InvalidProgramException.class, () -> eventDatesValidator.validate(programDayRequests));
    }
    
    @Test
    public void givenTwoIdenticalDates_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        when(festivalDates.getFestivalDays()).thenReturn(Arrays.asList(A_DATE));
        programDayRequests.add(new ProgramDayRequest(SOME_ACTIVITY_NAME, SOME_ARTIST_NAME, A_DATE.toString()));
        
        assertThrows(InvalidProgramException.class, () -> eventDatesValidator.validate(programDayRequests));
    }
  
    @Test
    public void givenTooManyEventDates_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        freezeFestivalDatesFromCurrentProgramDayRequests();
        programDayRequests.add(new ProgramDayRequest(SOME_ACTIVITY_NAME, SOME_ARTIST_NAME, ANOTHER_DATE.toString()));

        assertThrows(InvalidProgramException.class, () -> eventDatesValidator.validate(programDayRequests));
    }

    private void freezeFestivalDatesFromCurrentProgramDayRequests() {
        when(festivalDates.getFestivalDays()).thenReturn(
            programDayRequests
                .stream()
                .map(singleDayProgram -> singleDayProgram.eventDate)
                .collect(Collectors.toList())
        );
    }
}
