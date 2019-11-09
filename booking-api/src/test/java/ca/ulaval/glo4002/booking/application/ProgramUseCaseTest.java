package ca.ulaval.glo4002.booking.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenProducer;
import ca.ulaval.glo4002.booking.domain.program.Program;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReservation;

public class ProgramUseCaseTest {

    private TransportReservation transportReservation;
    private ShuttleRepository shuttleRepository;
    private ArtistRepository artistRepository;
    private Program program;
    private ProgramUseCase programUseCase;
    private OxygenProducer oxygenProducer;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;

    @BeforeEach
    public void setUp() {
        artistRepository = mock(ArtistRepository.class);
        transportReservation = mock(TransportReservation.class);
        shuttleRepository = mock(ShuttleRepository.class);
        programUseCase = new ProgramUseCase(shuttleRepository, transportReservation, oxygenProducer, artistRepository, oxygenInventoryRepository, oxygenHistoryRepository);
    }

    @Test
    public void givenProgram_provideProgramResources_thenProvideProgramResourcesfromProgramIsCalled() {
        program = mock(Program.class);

        programUseCase.provideProgramResources(program);

        verify(program).provideProgramResources(transportReservation, shuttleRepository, oxygenProducer, artistRepository, oxygenInventoryRepository, oxygenHistoryRepository);
    }
}