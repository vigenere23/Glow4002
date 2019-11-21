package ca.ulaval.glo4002.booking.domain.program;

import java.util.List;

public class Program {

    private List<ProgramDay> programDays;

    public Program(List<ProgramDay> programDays, ProgramValidator programValidator) {
        programValidator.validateProgram(programDays);
        this.programDays = programDays;
    }

    public List<ProgramDay> getDays() {
        return programDays;
    }
}
