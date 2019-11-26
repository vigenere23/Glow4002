package ca.ulaval.glo4002.booking.application.program;

import java.util.List;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.api.resources.program.requests.ProgramRequest;
import ca.ulaval.glo4002.booking.application.program.dtos.ProgramDayDtoMapper;
import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.program.Program;
import ca.ulaval.glo4002.booking.domain.program.ProgramDay;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class ProgramUseCase {

    @Inject private TransportReserver transportReserver;
    @Inject private OxygenRequester oxygenRequester;
    @Inject private PassRepository passRepository;
    @Inject private ArtistRepository artistRepository;
    @Inject private OutcomeSaver outcomeSaver;
    @Inject private ProgramValidator programValidator;
    @Inject private ProgramDayDtoMapper programDayDtoMapper;

    public void provideProgramResources(ProgramRequest programRequest) {
        List<ProgramDay> programDays = programDayDtoMapper.fromRequests(programRequest.programDays);
        Program program = new Program(programDays, programValidator);
        List<ArtistProgramInformation> artistsForProgram = artistRepository.getArtistsForProgram();
        
        for (ProgramDay programDay : program.getDays()) {
            int numberOfAttendees = passRepository.findAttendingAtDate(programDay.getDate()).size();
            programDay.orderShuttle(transportReserver, artistsForProgram);
            programDay.orderOxygen(oxygenRequester, artistsForProgram, numberOfAttendees);
            programDay.saveOutcome(outcomeSaver, artistsForProgram);
        }
    }
}
