package ca.ulaval.glo4002.booking.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.program.SingleDayProgram;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class ProgramUseCaseTest {

    private TransportReserver transportReserver;
    private ArtistRepository artistRepository;
    private ProgramUseCase programUseCase;
    private OxygenReserver oxygenReserver;
    private SingleDayProgram singleDayProgram;

    @BeforeEach
    public void setUp() {
        artistRepository = mock(ArtistRepository.class);
        transportReserver = mock(TransportReserver.class);
        singleDayProgram = mock(SingleDayProgram.class);
        programUseCase = new ProgramUseCase(transportReserver, oxygenReserver, artistRepository);
    }
}