package ca.ulaval.glo4002.booking.application.program;

import java.util.List;

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

    private TransportReserver transportReserver;
    private OxygenRequester oxygenRequester;
    private PassRepository passRepository;
    private ArtistRepository artistRepository;
    private OutcomeSaver outcomeSaver;
    private ProgramValidator programValidator;
    private ProgramDayDtoMapper programDayDtoMapper;

    public ProgramUseCase(
        TransportReserver transportReserver, OxygenRequester oxygenRequester, ArtistRepository artistRepository,
        PassRepository passRepository, OutcomeSaver outcomeSaver, ProgramValidator programValidator, ProgramDayDtoMapper programDayDtoMapper
    ) {
        this.transportReserver = transportReserver;
        this.oxygenRequester = oxygenRequester;
        this.artistRepository = artistRepository;
        this.passRepository = passRepository;
        this.outcomeSaver = outcomeSaver;
        this.programValidator = programValidator;
        this.programDayDtoMapper = programDayDtoMapper;
    }

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
