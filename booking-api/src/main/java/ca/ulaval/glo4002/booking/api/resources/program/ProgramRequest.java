package ca.ulaval.glo4002.booking.api.resources.program;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;

public class ProgramRequest {

    public final List<SingleDayProgramRequest> program;

    @JsonCreator
    public ProgramRequest(
        @JsonProperty(value = "program", required = true) List<SingleDayProgramRequest> program) {
        try {
            this.program = program;
        }
        catch (Exception exception) {
            throw new InvalidProgramException();
        }
    }
}