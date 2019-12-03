package ca.ulaval.glo4002.booking.application.program.dtos;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.program.ProgramDay;
import ca.ulaval.glo4002.booking.domain.program.ProgramDayFactory;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramDayRequest;

public class ProgramDayDtoMapper {

    @Inject private ProgramDayFactory programDayFactory;

    public List<ProgramDay> fromDtos(List<ProgramDayRequest> dtos) {
        return dtos
            .stream()
            .map(dto -> fromDto(dto))
            .collect(Collectors.toList());
    }

    public ProgramDay fromDto(ProgramDayRequest dto) {
        return programDayFactory.create(dto.eventDate, dto.artist, dto.activity);
    }
}
