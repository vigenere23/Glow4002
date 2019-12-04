package ca.ulaval.glo4002.booking.application.program;

import java.util.List;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.application.program.dtos.ProgramDayDtoMapper;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.program.ProgramDay;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;
import ca.ulaval.glo4002.booking.domain.program.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramRequest;

public class ProgramUseCase {

    @Inject private ProgramDayDtoMapper programDtoMapper;
    @Inject private ProgramValidator programValidator;
    @Inject private TransportReserver transportReserver;
    @Inject private OxygenRequester oxygenRequester;
    @Inject private OutcomeSaver outcomeSaver;

    public void provideProgramResources(ProgramRequest programRequest) {
        try {
            List<ProgramDay> programDays = programDtoMapper.fromDtos(programRequest.programDays);
            programValidator.validateProgramDays(programDays);
            
            for (ProgramDay programDay : programDays) {
                programDay.orderShuttle(transportReserver);
                programDay.orderOxygen(oxygenRequester);
                programDay.saveOutcome(outcomeSaver);
            }
        }
        catch (Exception exception) {
            throw new InvalidProgramException();
        }
    }
}
