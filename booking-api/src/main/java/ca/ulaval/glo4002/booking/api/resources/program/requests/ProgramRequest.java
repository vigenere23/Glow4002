package ca.ulaval.glo4002.booking.api.resources.program.requests;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProgramRequest {

    public final List<ProgramDayRequest> programDays;

    @JsonCreator
    public ProgramRequest(
        @JsonProperty(value = "program", required = true) List<ProgramDayRequest> programDays
    ) {
        this.programDays = programDays;
    }

    public List<ProgramDayRequest> getProgramDays() {
        return programDays;
    }
}
