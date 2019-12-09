package ca.ulaval.glo4002.booking.application.program.validators;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.program.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramDayRequest;

@ExtendWith(MockitoExtension.class)
public class ProgramValidatorTest {

    @Mock ProgramDayRequestValidator validator1;
    @Mock ProgramDayRequestValidator validator2;
    @Mock List<ProgramDayRequest> programDayRequests;
    private ProgramDayRequestValidatorContainer validatorContainer;

    @BeforeEach
    public void setup() {
        validatorContainer = new ProgramDayRequestValidatorContainer();
        validatorContainer.addValidator(validator1);
    }

    @Test
    public void givenMultipleValidators_whenValidating_itAsksEachValidatorToValidate() {
        validatorContainer.addValidator(validator2);

        validatorContainer.validate(programDayRequests);

        verify(validator1).validate(programDayRequests);
        verify(validator2).validate(programDayRequests);
    }

    @Test
    public void givenAFailingValidation_whenValidating_itThrowsAnInvalidProgramException() {
        doThrow(InvalidProgramException.class).when(validator1).validate(programDayRequests);
        assertThrows(InvalidProgramException.class, () -> validatorContainer.validate(programDayRequests));
    }
}
