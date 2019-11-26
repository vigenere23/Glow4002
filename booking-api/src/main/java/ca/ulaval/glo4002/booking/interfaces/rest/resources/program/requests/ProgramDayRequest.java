package ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.program.Activity;

public class ProgramDayRequest {

    public final LocalDate eventDate;
    public final Activity activity;
    public final String artist;

    @JsonCreator
    public ProgramDayRequest (
        @JsonProperty(value = "am", required = true) String activity,
        @JsonProperty(value = "pm", required = true) String artist,
        @JsonProperty(value = "eventDate", required = true) String eventDate
    ) {
        try {
            this.eventDate = LocalDate.parse(eventDate);
            this.artist = artist;
            this.activity = Activity.fromString(activity);
        }
        catch (Exception exception) {
            throw new InvalidProgramException();
        }
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getArtist() {
        return artist;
    }
}
