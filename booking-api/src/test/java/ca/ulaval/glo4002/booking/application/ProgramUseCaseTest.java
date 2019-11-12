package ca.ulaval.glo4002.booking.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

    private static final LocalDate SOME_DATE = LocalDate.of(2050, 07, 18);
    private static final String SOME_ARTIST_NAME = "Sun 41";

    private TransportReserver transportReserver;
    private ArtistRepository artistRepository;
    private ProgramUseCase programUseCase;
    private List<SingleDayProgram> program = new ArrayList<>();
    private OxygenReserver oxygenReserver;
    private SingleDayProgram singleDay;
    private PassRepository passRepository;
    private ArtistProgramInformation artistProgramInformation;
    private FestivalAttendeesCounter festivalAttendeesCounter;

    @BeforeEach
    public void setUpProgramUseCase() {
        singleDay = mock(SingleDayProgram.class);
        artistProgramInformation = mock(ArtistProgramInformation.class);
        artistRepository = mock(ArtistRepository.class);
        transportReserver = mock(TransportReserver.class);
        oxygenReserver = mock(OxygenReserver.class);
        passRepository = mock(PassRepository.class);
        festivalAttendeesCounter = mock(FestivalAttendeesCounter.class);

        when(singleDay.getDate()).thenReturn(SOME_DATE);
        when(singleDay.getArtist()).thenReturn(SOME_ARTIST_NAME);

        program.add(singleDay);
        mockArtistForProgram();

        programUseCase = new ProgramUseCase(transportReserver, oxygenReserver, artistRepository, passRepository, festivalAttendeesCounter);
    }

    @Test
    public void givenProgram_whenProvideProgramResources_thenOrderShuttles() {
        programUseCase.provideProgramResources(program);

        verify(singleDay).orderShuttle(transportReserver, artistRepository.getArtistsForProgram());
    }

    @Test
    public void givenProgram_whenProvideProgramResources_thenOrderOxygen() {
        programUseCase.provideProgramResources(program);

        verify(singleDay).orderOxygen(oxygenReserver,  artistRepository.getArtistsForProgram(), festivalAttendeesCounter.countFestivalAttendeesForOneDay(passRepository.findAll(), SOME_DATE));
    }

    @Test
    public void givenProgram_whenProvideProgramResources_thenCountFestivalAttendeesForOneDay() {
        programUseCase.provideProgramResources(program);

        verify(festivalAttendeesCounter).countFestivalAttendeesForOneDay(passRepository.findAll(), SOME_DATE);
    }

    private void mockArtistForProgram() {
        List<ArtistProgramInformation> artistsForProgram = new ArrayList<>();
        artistsForProgram.add(artistProgramInformation);

        when(artistRepository.getArtistsForProgram()).thenReturn(artistsForProgram);
    }
}