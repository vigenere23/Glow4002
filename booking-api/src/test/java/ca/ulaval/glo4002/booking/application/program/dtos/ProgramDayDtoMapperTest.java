package ca.ulaval.glo4002.booking.application.program.dtos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.program.Activity;
import ca.ulaval.glo4002.booking.domain.program.ProgramDay;
import ca.ulaval.glo4002.booking.domain.program.ProgramDayFactory;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramDayRequest;

@ExtendWith(MockitoExtension.class)
public class ProgramDayDtoMapperTest {

    private ProgramDayRequest programDayRequest;

    @Mock ProgramDay returnedProgramDay;
    @Mock ProgramDayFactory programDayFactory;
    @InjectMocks ProgramDayDtoMapper programDayDtoMapper;

    @BeforeEach
    public void setup() {
        programDayRequest = createProgramDayRequest();
        when(programDayFactory.create(programDayRequest.eventDate, programDayRequest.artist, programDayRequest.activity))
            .thenReturn(returnedProgramDay);
    }

    @Test
    public void whenMappingFromDto_itAsksFactoryForCreation() {
        programDayDtoMapper.fromDto(programDayRequest);
        verify(programDayFactory).create(programDayRequest.eventDate, programDayRequest.artist, programDayRequest.activity);
    }

    @Test
    public void whenMappingFromDto_itReturnsTheCorrespondingDomainObjectFromFactory() {
        ProgramDay programDay = programDayDtoMapper.fromDto(programDayRequest);
        assertThat(programDay).isEqualTo(returnedProgramDay);
    }

    private ProgramDayRequest createProgramDayRequest() {
        return new ProgramDayRequest(Activity.CARDIO.toString(), "John Pecker", LocalDate.now().toString());
    }
}
