package ca.ulaval.glo4002.booking.api.resources.program.dto;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.api.resources.program.ProgramRequest;
import ca.ulaval.glo4002.booking.api.resources.program.SingleDayProgramRequest;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;
import ca.ulaval.glo4002.booking.domain.program.SingleDayProgram;

public class ProgramMapper {

    public List<SingleDayProgram> fromDto(ProgramRequest programRequest, ProgramValidator programValidator) {
        List<SingleDayProgram> program = fromSingleDayDto(programRequest);
        programValidator.validateProgram(program);
        return program;
    }

    private List<SingleDayProgram> fromSingleDayDto(ProgramRequest programRequest) {
        List<SingleDayProgram> program = new ArrayList<>();
        for(SingleDayProgramRequest singleDayProgram : programRequest.program) {
            program.add(new SingleDayProgram(singleDayProgram.activity, singleDayProgram.artist, singleDayProgram.eventDate));
        }
        return program;
    }
}