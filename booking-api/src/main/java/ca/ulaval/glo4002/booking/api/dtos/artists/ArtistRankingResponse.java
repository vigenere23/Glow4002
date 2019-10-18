package ca.ulaval.glo4002.booking.api.dtos.artists;

import java.util.LinkedList;
import java.util.List;

public class ArtistRankingResponse {
    
    public List<String> artistRanked = new LinkedList<>();

	public ArtistRankingResponse(List<String> artistsRanked) {
        this.artistRanked = artistsRanked;
    }
}
