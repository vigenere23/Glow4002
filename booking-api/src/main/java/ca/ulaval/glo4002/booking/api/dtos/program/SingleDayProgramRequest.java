package ca.ulaval.glo4002.booking.api.dtos.program;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.domain.program.Activity;

public class SingleDayProgramRequest {

    public final LocalDate eventDate;
    public final Activity activity;
    public final String artist;

    @JsonCreator
    public SingleDayProgramRequest (
        @JsonProperty(value = "am", required = true) String activity,
        @JsonProperty(value = "pm", required = true) String artist,
        @JsonProperty(value = "eventDate", required = true) String eventDate
    ) throws InvalidFormatException {
        try {
            this.eventDate = LocalDate.parse(eventDate);
            this.artist = artist;
            this.activity = Activity.fromString(activity);
        }
        catch (Exception exception) {
            System.out.println("cac");
            throw new InvalidFormatException();
        }
    }
}
