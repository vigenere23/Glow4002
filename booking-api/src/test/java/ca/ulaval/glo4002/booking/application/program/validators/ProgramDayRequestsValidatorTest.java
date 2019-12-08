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
public class ProgramDayRequestsValidatorTest {

    private final static String AN_ACTIVITY_NAME = Activity.CARDIO.toString();
    private final static String AN_ARTIST_NAME = "Lord Gasper";
    private final static String ANOTHER_ARTIST_NAME = "Sweet Symphony";
    private final static LocalDate A_DATE = LocalDate.now();
    private final static LocalDate ANOTHER_DATE = A_DATE.plusDays(1);
    
    private List<ProgramDayRequest> programDayRequests;

    @Mock FestivalDates festivalDates;
    @InjectMocks ProgramDayRequestValidator programDayRequestsValidator;

    @BeforeEach
    public void setUpProgramCreation() {
        programDayRequests = new ArrayList<>();
        programDayRequests.add(new ProgramDayRequest(AN_ACTIVITY_NAME, AN_ARTIST_NAME, A_DATE.toString()));
    }

    @Test
    public void givenValidProgramDayRequests_whenValidating_thenProgramIsCreatedWithoutError() {
        programDayRequests.add(new ProgramDayRequest(AN_ACTIVITY_NAME, ANOTHER_ARTIST_NAME, ANOTHER_DATE.toString()));
        freezeFestivalDatesFromCurrentProgramDayRequests();
        
        assertDoesNotThrow(() -> programDayRequestsValidator.validate(programDayRequests));
    }

    public void givenOutOfFestivalDate_whenValidating_thenAnInvalidProgramExceptionIsThrown() {
        freezeFestivalDatesFromCurrentProgramDayRequests();
        programDayRequests.add(new ProgramDayRequest(AN_ACTIVITY_NAME, ANOTHER_ARTIST_NAME, A_DATE.toString()));

        assertThrows(InvalidProgramException.class, () -> programDayRequestsValidator.validate(programDayRequests));
    }
    
    @Test
    public void givenTwoIdenticalDates_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        when(festivalDates.getFestivalDays()).thenReturn(Arrays.asList(A_DATE));
        programDayRequests.add(new ProgramDayRequest(AN_ACTIVITY_NAME, ANOTHER_ARTIST_NAME, A_DATE.toString()));
        
        assertThrows(InvalidProgramException.class, () -> programDayRequestsValidator.validate(programDayRequests));
    }
  
    @Test
    public void givenTooLongProgram_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        freezeFestivalDatesFromCurrentProgramDayRequests();
        programDayRequests.add(new ProgramDayRequest(AN_ACTIVITY_NAME, ANOTHER_ARTIST_NAME, ANOTHER_DATE.toString()));

        assertThrows(InvalidProgramException.class, () -> programDayRequestsValidator.validate(programDayRequests));
    }

    @Test
    public void givenSameArtistForTwoDays_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        programDayRequests.add(new ProgramDayRequest(AN_ACTIVITY_NAME, AN_ARTIST_NAME, ANOTHER_DATE.toString()));

        assertThrows(InvalidProgramException.class, () -> programDayRequestsValidator.validate(programDayRequests));
    }

    @Test
    public void givenActivityStringForPm_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        ProgramDayRequest singleDay = new ProgramDayRequest(AN_ACTIVITY_NAME, AN_ACTIVITY_NAME, ANOTHER_DATE.toString());
        programDayRequests.set(0 , singleDay);

        assertThrows(InvalidProgramException.class, () -> programDayRequestsValidator.validate(programDayRequests));
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
