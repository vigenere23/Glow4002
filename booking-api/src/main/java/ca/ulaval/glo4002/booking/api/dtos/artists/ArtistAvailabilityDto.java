package ca.ulaval.glo4002.booking.api.dtos.artists;

import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ArtistAvailabilityDto {
    public LocalDate availability;

    @JsonCreator
    public ArtistAvailabilityDto(
            @JsonProperty(value = "availability") String availability
    ) throws InvalidFormatException {
        try {
            this.availability = LocalDate.parse(availability);
        }
        catch (Exception exception) {
            throw new InvalidFormatException();
        }
    }
}
