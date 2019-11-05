package ca.ulaval.glo4002.booking.api.dtos.program;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;

public class ProgramRequest {

    public final List<SingleDayProgramRequest> program;

    @JsonCreator
    public ProgramRequest(
        @JsonProperty(value = "program", required = true) List<SingleDayProgramRequest> program
    )  throws InvalidFormatException {
        try {
            this.program = program;
            System.out.println("prog");
        }
        catch (Exception exception) {
            throw new InvalidFormatException();
        }
    }
}