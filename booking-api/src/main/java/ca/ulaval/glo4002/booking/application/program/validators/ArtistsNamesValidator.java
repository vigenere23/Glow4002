package ca.ulaval.glo4002.booking.application.program.validators;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.program.Activity;
import ca.ulaval.glo4002.booking.domain.program.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.helpers.enums.EnumExistenceChecker;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramDayRequest;

public class ArtistsNamesValidator implements ProgramDayRequestValidator {

    @Override
    public void validate(List<ProgramDayRequest> programDayRequests) {
        List<String> artistNames = programDayRequests
            .stream()
            .map(programDayRequest -> programDayRequest.artist)
            .collect(Collectors.toList());
        
        boolean isValid = !artistNames
            .stream()
            .filter(artistName -> EnumExistenceChecker.isInEnum(artistName, Activity.class))
            .findFirst()
            .isPresent();

        if (!isValid) {
            throw new InvalidProgramException();
        }
    }
}
