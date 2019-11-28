package ca.ulaval.glo4002.booking.application.program;

import java.util.List;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramRequest;
import ca.ulaval.glo4002.booking.application.program.dtos.ProgramDayDtoMapper;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.program.Program;
import ca.ulaval.glo4002.booking.domain.program.ProgramDay;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class ProgramUseCase {

    @Inject private TransportReserver transportReserver;
    @Inject private OxygenRequester oxygenRequester;
    @Inject private ArtistRepository artistRepository;
    @Inject private OutcomeSaver outcomeSaver;
    @Inject private ProgramValidator programValidator;
    @Inject private ProgramDayDtoMapper programDayDtoMapper;

    public void provideProgramResources(ProgramRequest programRequest) {
        List<Artist> artistsForProgram = artistRepository.findAll();
        List<ProgramDay> programDays = programDayDtoMapper.fromDtoAndArtists(programRequest.programDays, artistsForProgram);
        programValidator.validateProgram(programDays);
        Program program = new Program(programDays);
        
        for (ProgramDay programDay : program.getDays()) {
            programDay.orderShuttle(transportReserver);
            programDay.orderOxygen(oxygenRequester);
            programDay.saveOutcome(outcomeSaver);
        }
    }
}
