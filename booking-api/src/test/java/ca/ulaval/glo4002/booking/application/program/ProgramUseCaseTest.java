package ca.ulaval.glo4002.booking.application.program;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.api.resources.program.requests.ProgramRequest;
import ca.ulaval.glo4002.booking.application.program.dtos.ProgramDayDtoMapper;
import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.program.ProgramDay;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class ProgramUseCaseTest {

    private final static LocalDate SOME_DATE = LocalDate.of(2050, 07, 18);
    private final static String SOME_ARTIST_NAME = "Sun 41";
    private final static int NUMBER_OF_ATTENDEES = 10;

    private TransportReserver transportReserver;
    private ArtistRepository artistRepository;
    private ProgramUseCase programUseCase;
    private List<ProgramDay> program = new ArrayList<>();
    private OxygenRequester oxygenRequester;
    private ProgramDay singleDay;
    private PassRepository passRepository;
    private ArtistProgramInformation artistProgramInformation;
    private List<ArtistProgramInformation> artistsForProgram;
    private OutcomeSaver outcomeSaver;
    private ProgramValidator programValidator;
    private ProgramDayDtoMapper programDayDtoMapper;
    private ProgramRequest programRequest;

    @BeforeEach
    public void setUpProgramUseCase() {
        singleDay = mock(ProgramDay.class);
        artistProgramInformation = mock(ArtistProgramInformation.class);
        artistRepository = mock(ArtistRepository.class);
        transportReserver = mock(TransportReserver.class);
        oxygenRequester = mock(OxygenRequester.class);
        passRepository = mock(PassRepository.class);
        outcomeSaver = mock(OutcomeSaver.class);
        programValidator = mock(ProgramValidator.class);
        programDayDtoMapper = mock(ProgramDayDtoMapper.class);
        programRequest = mock(ProgramRequest.class);

        when(singleDay.getDate()).thenReturn(SOME_DATE);
        when(singleDay.getArtist()).thenReturn(SOME_ARTIST_NAME);

        program.add(singleDay);
        
        when(programDayDtoMapper.fromRequests(any())).thenReturn(program);

        mockPassRepository();
        mockArtistForProgram();

        programUseCase = new ProgramUseCase(transportReserver, oxygenRequester, artistRepository, passRepository, outcomeSaver, programValidator, programDayDtoMapper);
    }

    @Test
    public void whenProvideProgramResources_thenOrderShuttles() {
        programUseCase.provideProgramResources(programRequest);
        verify(singleDay).orderShuttle(transportReserver, artistsForProgram);
    }

    @Test
    public void whenProvideProgramResources_thenSaveOutcome() {
        programUseCase.provideProgramResources(programRequest);
        verify(singleDay).saveOutcome(outcomeSaver, artistsForProgram);
    }

    @Test
    public void whenProvideProgramResources_thenOrderOxygen() {
        programUseCase.provideProgramResources(programRequest);
        verify(singleDay).orderOxygen(oxygenRequester,  artistRepository.getArtistsForProgram(), NUMBER_OF_ATTENDEES);
    }

    private void mockArtistForProgram() {
        artistsForProgram = new ArrayList<>();
        artistsForProgram.add(artistProgramInformation);

        when(artistRepository.getArtistsForProgram()).thenReturn(artistsForProgram);
    }

    private void mockPassRepository() {
        List<Pass> passes = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_ATTENDEES; i++) {
            passes.add(mock(Pass.class));
        }
        when(passRepository.findAttendingAtDate(any(LocalDate.class))).thenReturn(passes);
    }
}