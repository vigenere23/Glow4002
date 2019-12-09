package ca.ulaval.glo4002.booking.application.program.validators;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.program.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramDayRequest;

public class ArtistsUniquenessValidator implements ProgramDayRequestValidator {

    @Override
    public void validate(List<ProgramDayRequest> programDayRequests) {
        List<String> artistNames = programDayRequests
            .stream()
            .map(programDayRequest -> programDayRequest.artist)
            .collect(Collectors.toList());
        Set<String> uniqueArtistNames = new HashSet<>(artistNames);

        boolean isValid = artistNames.size() == uniqueArtistNames.size();
        
        if (!isValid) {
            throw new InvalidProgramException();
        }
    }
}
