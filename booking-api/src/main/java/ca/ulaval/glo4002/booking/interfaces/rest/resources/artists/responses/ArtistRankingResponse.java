package ca.ulaval.glo4002.booking.interfaces.rest.resources.artists.responses;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.artists.Artist;

public class ArtistRankingResponse {  
    public final List<String> artists;

    public ArtistRankingResponse(List<Artist> artists) {
        this.artists = artists
            .stream()
            .map(Artist::getName)
            .collect(Collectors.toList());
    }
}
