package ca.ulaval.glo4002.booking.api.dtos.artists;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ArtistDto {
    public int id;
    public String name;
    public int nbPeople;
    public String musicStyle;
    public float price;
    public int popularityRank;
    public List<ArtistAvailabilityDto> availabilities;

    @JsonCreator
    public ArtistDto(
            @JsonProperty(value = "id", required = true) int id,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "nbPeople", required = true) int nbPeople,
            @JsonProperty(value = "musicStyle") String musicStyle,
            @JsonProperty(value = "price", required = true) float price,
            @JsonProperty(value = "popularityRank", required = true) int popularityRank,
            @JsonProperty(value = "availabilities") List<ArtistAvailabilityDto> availabilities
    ) {
        this.id = id;
        this.name = name;
        this.nbPeople = nbPeople;
        this.musicStyle = musicStyle;
        this.price = price;
        this.popularityRank = popularityRank;
        this.availabilities = availabilities;
    }
}
