package ca.ulaval.glo4002.booking.api.dtoMappers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.resources.program.ProgramRequest;
import ca.ulaval.glo4002.booking.api.resources.program.SingleDayProgramRequest;
import ca.ulaval.glo4002.booking.api.resources.program.dto.ProgramMapper;
import ca.ulaval.glo4002.booking.domain.program.Activity;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;
import ca.ulaval.glo4002.booking.domain.program.SingleDayProgram;

public class ProgramMapperTest {

    private final static String SOME_DATE = LocalDate.of(2050, 7, 17).toString();
    private final static String SOME_ACTIVITY = Activity.YOGA.toString();
    private final static String SOME_ARTIST = "Kid Rocket";
    
    private ProgramMapper programMapper;
    private ProgramRequest programRequest;
    private ProgramValidator programValidator;
    private List<SingleDayProgramRequest> programDto = new ArrayList<>();
    private SingleDayProgramRequest singleDayProgramRequest;

    @BeforeEach
    public void setupProgramMapper() {
        programValidator = mock(ProgramValidator.class);

        singleDayProgramRequest = new SingleDayProgramRequest(SOME_ACTIVITY, SOME_ARTIST, SOME_DATE);
        programDto.add(singleDayProgramRequest);
        programRequest = new ProgramRequest(programDto);
        programMapper = new ProgramMapper();
    }

    @Test
    public void whenMappingFromRequestDto_thenReturnEquivalentProgram() {
        List<SingleDayProgram> program = programMapper.fromDto(programRequest, programValidator);

        assertEquals(SOME_ARTIST, program.get(0).getArtist());
        assertEquals(SOME_DATE, program.get(0).getDate().toString());
    }
}