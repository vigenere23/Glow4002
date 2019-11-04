package ca.ulaval.glo4002.booking.api.dtos.program;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.helpers.DateFormatter;

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
            this.eventDate = LocalDate.parse(eventDate, DateFormatter.formatter);
            this.artist = artist;
            this.activity = Activity.fromString(activity);
        }
        catch (Exception exception) {
            throw new InvalidFormatException();
        }
    }
}
