package ca.ulaval.glo4002.booking.api.dtos.artists;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class ArtistAvailabilityDto {
    public LocalDate availability;

    @JsonCreator
    public ArtistAvailabilityDto(
            @JsonProperty(value = "availability") String availability
    ) {
        this.availability = LocalDate.parse(availability);
    }
}
