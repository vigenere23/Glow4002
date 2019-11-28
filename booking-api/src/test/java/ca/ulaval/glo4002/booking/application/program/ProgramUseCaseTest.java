package ca.ulaval.glo4002.booking.application.program;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramRequest;
import ca.ulaval.glo4002.booking.application.program.dtos.ProgramDayDtoMapper;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.program.ProgramDay;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

@ExtendWith(MockitoExtension.class)
public class ProgramUseCaseTest {

    private final static LocalDate SOME_DATE = LocalDate.of(2050, 07, 18);
    private final static int NUMBER_OF_ATTENDEES = 10;

    private List<Artist> artistsForProgram;

    @Mock TransportReserver transportReserver;
    @Mock ArtistRepository artistRepository;
    @Mock OxygenRequester oxygenRequester;
    @Mock ProgramDay programDay;
    @Mock PassRepository passRepository;
    @Mock Artist artist;
    @Mock OutcomeSaver outcomeSaver;
    @Mock ProgramValidator programValidator;
    @Mock ProgramDayDtoMapper programDayDtoMapper;
    @Mock ProgramRequest programRequest;
    @Mock Pass pass;
    @InjectMocks ProgramUseCase programUseCase;

    @BeforeEach
    public void setUpProgramUseCase() {
        when(programDay.getDate()).thenReturn(SOME_DATE);

        mockProgramDayDtoMapper();
        mockPassRepository();
        mockArtistForProgram();
    }

    @Test
    public void whenProvideProgramResources_thenOrderShuttles() {
        programUseCase.provideProgramResources(programRequest);
        verify(programDay).orderShuttle(transportReserver);
    }

    @Test
    public void whenProvideProgramResources_thenSaveOutcome() {
        programUseCase.provideProgramResources(programRequest);
        verify(programDay).saveOutcome(outcomeSaver);
    }

    @Test
    public void whenProvideProgramResources_thenOrderOxygen() {
        programUseCase.provideProgramResources(programRequest);
        verify(programDay).orderOxygen(oxygenRequester, NUMBER_OF_ATTENDEES);
    }

    private void mockProgramDayDtoMapper() {
        List<ProgramDay> programDays = new ArrayList<>();
        programDays.add(programDay);
        when(programDayDtoMapper.fromDtoAndArtists(any(), any())).thenReturn(programDays);
    }

    private void mockArtistForProgram() {
        artistsForProgram = new ArrayList<>();
        artistsForProgram.add(artist);

        when(artistRepository.findAll()).thenReturn(artistsForProgram);
    }

    private void mockPassRepository() {
        List<Pass> passes = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_ATTENDEES; i++) {
            passes.add(pass);
        }
        when(passRepository.findAttendingAtDate(any(LocalDate.class))).thenReturn(passes);
    }
}
