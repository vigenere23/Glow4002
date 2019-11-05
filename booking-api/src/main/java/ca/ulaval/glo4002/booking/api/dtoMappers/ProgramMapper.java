package ca.ulaval.glo4002.booking.api.dtoMappers;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.api.dtos.program.ProgramRequest;
import ca.ulaval.glo4002.booking.api.dtos.program.SingleDayProgramRequest;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.program.Program;
import ca.ulaval.glo4002.booking.domain.program.SingleDayProgram;

public class ProgramMapper {

    public Program fromDto(ProgramRequest programRequest, FestivalDates glow4002Dates) {
        return new Program(fromSingleDayDto(programRequest), glow4002Dates);
    }

    private List<SingleDayProgram> fromSingleDayDto(ProgramRequest programRequest) {
        List<SingleDayProgram> singleDaysProgram = new ArrayList<>();
        for(SingleDayProgramRequest singleDayProgram : programRequest.program) {
            singleDaysProgram.add(new SingleDayProgram(singleDayProgram.activity, singleDayProgram.artist, singleDayProgram.eventDate));
        }
        return singleDaysProgram;
    }
}
