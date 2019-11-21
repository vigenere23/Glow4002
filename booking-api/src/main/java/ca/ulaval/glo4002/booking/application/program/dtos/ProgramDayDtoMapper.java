package ca.ulaval.glo4002.booking.application.program.dtos;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.api.resources.program.requests.ProgramDayRequest;
import ca.ulaval.glo4002.booking.domain.program.ProgramDay;

public class ProgramDayDtoMapper {

    public List<ProgramDay> fromRequests(List<ProgramDayRequest> programDayRequests) {
        return programDayRequests
            .stream()
            .map(programDayRequest -> fromRequest(programDayRequest))
            .collect(Collectors.toList());
    }

    private ProgramDay fromRequest(ProgramDayRequest programDayRequest) {
        return new ProgramDay(programDayRequest.activity, programDayRequest.artist, programDayRequest.eventDate);
    }
}
