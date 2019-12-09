package ca.ulaval.glo4002.booking.application.program.validators;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramDayRequest;

public class ProgramDayRequestValidatorContainer implements ProgramDayRequestValidator {
    
    private List<ProgramDayRequestValidator> validators;

    public ProgramDayRequestValidatorContainer() {
        validators = new ArrayList<>();
    }

    public void addValidator(ProgramDayRequestValidator validator) {
        this.validators.add(validator);
    }

    public void validate(List<ProgramDayRequest> programDayRequests) {
        for (ProgramDayRequestValidator validationRule : validators) {
            validationRule.validate(programDayRequests);
        }
    }
}
