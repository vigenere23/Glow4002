package ca.ulaval.glo4002.booking.api.resources.artists;

import java.util.ArrayList;
import java.util.List;

public class ArtistRankingResponse {
    
    public List<String> artists = new ArrayList<>();

	public ArtistRankingResponse(List<String> artists) {
        this.artists = artists;
    }
}
