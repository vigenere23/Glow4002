package ca.ulaval.glo4002.booking.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.passes.PassCounter;
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
    private PassCounter passCounter;

    @BeforeEach
    public void setUpProgramUseCase() {
        singleDay = mock(SingleDayProgram.class);
        artistRepository = mock(ArtistRepository.class);
        transportReserver = mock(TransportReserver.class);
        oxygenReserver = mock(OxygenReserver.class);
        passRepository = mock(PassRepository.class);
        passCounter = mock(PassCounter.class);

        programUseCase = new ProgramUseCase(transportReserver, oxygenReserver, artistRepository, passRepository, passCounter);
    }

    @Test
    public void givenProgram_whenProvideProgramResources_thenOrderShuttles() {
        List<SingleDayProgram> program = new ArrayList<>();
        program.add(singleDay);

        programUseCase.provideProgramResources(program);

        verify(singleDay).orderShuttle(transportReserver, artistRepository);
    }
}