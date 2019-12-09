package ca.ulaval.glo4002.booking.application.program.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.program.Activity;
import ca.ulaval.glo4002.booking.domain.program.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramDayRequest;

public class ArtistsUniquenessValidatorTest {

    private final static String SOME_ACTIVITY_NAME = Activity.CARDIO.toString();
    private final static String AN_ARTIST_NAME = "Lord Gasper";
    private final static String ANOTHER_ARTIST_NAME = "Lala Loca";
    private final static LocalDate SOME_DATE = LocalDate.now();
    
    private List<ProgramDayRequest> programDayRequests;
    private ArtistsUniquenessValidator artistsUniquenessValidator;

    @BeforeEach
    public void setUpProgramRequests() {
        artistsUniquenessValidator = new ArtistsUniquenessValidator();
        programDayRequests = new ArrayList<>();
    }

    @Test
    public void givenValidProgramDayRequests_whenValidating_thenProgramIsCreatedWithoutError() {
        programDayRequests.add(new ProgramDayRequest(SOME_ACTIVITY_NAME, AN_ARTIST_NAME, SOME_DATE.toString()));
        programDayRequests.add(new ProgramDayRequest(SOME_ACTIVITY_NAME, ANOTHER_ARTIST_NAME, SOME_DATE.toString()));
        
        assertDoesNotThrow(() -> artistsUniquenessValidator.validate(programDayRequests));
    }
    
    @Test
    public void givenSameArtistForTwoDays_whenValidateProgram_thenInvalidProgramExceptionIsThrown() {
        programDayRequests.add(new ProgramDayRequest(SOME_ACTIVITY_NAME, AN_ARTIST_NAME, SOME_DATE.toString()));
        programDayRequests.add(new ProgramDayRequest(SOME_ACTIVITY_NAME, AN_ARTIST_NAME, SOME_DATE.toString()));

        assertThrows(InvalidProgramException.class, () -> artistsUniquenessValidator.validate(programDayRequests));
    }
}
