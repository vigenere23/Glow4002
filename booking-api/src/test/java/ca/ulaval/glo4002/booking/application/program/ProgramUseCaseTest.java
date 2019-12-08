package ca.ulaval.glo4002.booking.application.program;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.application.program.dtos.ProgramDayDtoMapper;
import ca.ulaval.glo4002.booking.application.program.validators.ProgramDayRequestValidator;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.program.ProgramDay;
import ca.ulaval.glo4002.booking.domain.program.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramRequest;

@ExtendWith(MockitoExtension.class)
public class ProgramUseCaseTest {

    private List<Artist> artistsForProgram;

    @Mock ProgramDay programDay;
    @Mock Artist artist;
    @Mock ProgramRequest programRequest;
    @Mock ProgramDayDtoMapper programDayDtoMapper;
    @Mock ProgramDayRequestValidator programValidator;
    @Mock TransportReserver transportReserver;
    @Mock OxygenRequester oxygenRequester;
    @Mock OutcomeSaver outcomeSaver;
    @InjectMocks ProgramUseCase programUseCase;

    @BeforeEach
    public void setUpProgramUseCase() {
        mockArtistForProgram();
    }

    @Test
    public void whenProvideProgramResources_thenOrderShuttles() {
        mockProgramDayDtoMapper();
        programUseCase.provideProgramResources(programRequest);
        verify(programDay).orderShuttle(transportReserver);
    }

    @Test
    public void whenProvideProgramResources_thenOrderOxygen() {
        mockProgramDayDtoMapper();
        programUseCase.provideProgramResources(programRequest);
        verify(programDay).orderOxygen(oxygenRequester);
    }

    @Test
    public void whenProvideProgramResources_thenSaveOutcome() {
        mockProgramDayDtoMapper();
        programUseCase.provideProgramResources(programRequest);
        verify(programDay).saveOutcome(outcomeSaver);
    }

    @Test
    public void whenProgramDayRequestsValidationFails_itDoesNotOrderShuttles() {
        doThrow(InvalidProgramException.class).when(programValidator).validate(any());
        try {
            programUseCase.provideProgramResources(programRequest);
        }
        catch (InvalidProgramException exception) {
            verify(programDay, times(0)).orderShuttle(transportReserver);
        }
    }

    @Test
    public void whenProgramDayRequestsValidationFails_itDoesNotOrderOxygen() {
        doThrow(InvalidProgramException.class).when(programValidator).validate(any());
        try {
            programUseCase.provideProgramResources(programRequest);
        }
        catch (InvalidProgramException exception) {
            verify(programDay, times(0)).orderOxygen(oxygenRequester);
        }
    }

    @Test
    public void whenProgramDayRequestsValidationFails_itDoesNotSaveOutcome() {
        doThrow(InvalidProgramException.class).when(programValidator).validate(any());
        try {
            programUseCase.provideProgramResources(programRequest);
        }
        catch (InvalidProgramException exception) {
            verify(programDay, times(0)).saveOutcome(outcomeSaver);
        }
    }

    private void mockProgramDayDtoMapper() {
        List<ProgramDay> programDays = new ArrayList<>();
        programDays.add(programDay);
        when(programDayDtoMapper.fromDtos(any())).thenReturn(programDays);
    }

    private void mockArtistForProgram() {
        artistsForProgram = new ArrayList<>();
        artistsForProgram.add(artist);
    }
}
