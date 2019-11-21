package ca.ulaval.glo4002.booking.api.resources.artists.responses;

import java.util.List;

public class ArtistRankingResponse {  
    public final List<String> artists;

    public ArtistRankingResponse(List<String> artists) {
        this.artists = artists;
    }
}
