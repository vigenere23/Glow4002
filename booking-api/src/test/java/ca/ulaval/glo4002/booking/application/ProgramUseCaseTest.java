package ca.ulaval.glo4002.booking.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.passes.FestivalAttendeesCounter;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.program.SingleDayProgram;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class ProgramUseCaseTest {

    private TransportReserver transportReserver;
    private ArtistRepository artistRepository;
    private ProgramUseCase programUseCase;
    private OxygenReserver oxygenReserver;
    private SingleDayProgram singleDay;
    private PassRepository passRepository;
    private ArtistProgramInformation artistProgramInformation;
    private FestivalAttendeesCounter festivalAttendeesCounter;
    private List<ArtistProgramInformation> artistsForProgram;

    @BeforeEach
    public void setUpProgramUseCase() {
        singleDay = mock(SingleDayProgram.class);
        artistProgramInformation = mock(ArtistProgramInformation.class);
        artistRepository = mock(ArtistRepository.class);
        transportReserver = mock(TransportReserver.class);
        oxygenReserver = mock(OxygenReserver.class);
        passRepository = mock(PassRepository.class);
        festivalAttendeesCounter = mock(FestivalAttendeesCounter.class);


        mockArtistForProgram();

        programUseCase = new ProgramUseCase(transportReserver, oxygenReserver, artistRepository, passRepository, festivalAttendeesCounter);
    }

    @Test
    public void givenProgram_whenProvideProgramResources_thenOrderShuttles() {
        List<SingleDayProgram> program = new ArrayList<>();
        program.add(singleDay);

        programUseCase.provideProgramResources(program);

        verify(singleDay).orderShuttle(transportReserver, artistsForProgram);
    }

    private void mockArtistForProgram() {
        artistsForProgram = new ArrayList<>();
        artistsForProgram.add(artistProgramInformation);

        when(artistRepository.getArtistsForProgram()).thenReturn(artistsForProgram);
    }

    //TODO add oxy test
}