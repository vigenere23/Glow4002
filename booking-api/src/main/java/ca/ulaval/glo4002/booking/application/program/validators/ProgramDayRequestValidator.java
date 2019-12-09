package ca.ulaval.glo4002.booking.application.program.validators;

import java.util.List;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramDayRequest;

public interface ProgramDayRequestValidator {
    void validate(List<ProgramDayRequest> programDayRequests);
}
