package ca.ulaval.glo4002.booking.api.resources.artists;

import java.util.LinkedList;
import java.util.List;

public class ArtistRankingResponse {
    
    public List<String> artists = new LinkedList<>();

	public ArtistRankingResponse(List<String> artists) {
        this.artists = artists;
    }
}
