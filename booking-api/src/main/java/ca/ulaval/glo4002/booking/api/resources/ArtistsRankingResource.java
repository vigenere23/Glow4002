package ca.ulaval.glo4002.booking.api.resources;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.api.dtos.artists.ArtistRankingResponse;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingService;
import ca.ulaval.glo4002.booking.domain.artists.Ranking;

@Path("/program/artists")
@Produces(MediaType.APPLICATION_JSON)
public class ArtistsRankingResource {
    private ArtistRankingService artistRankingService;
    private  static final HashMap<String, Ranking> ranking = new HashMap<String, Ranking>() {{
        put("lowCosts", Ranking.LOW_COSTS);
        put("mostPopular", Ranking.MOST_POPULARITY);
    }};
    
    @Inject
    public ArtistsRankingResource() {
        artistRankingService = new ArtistRankingService();
    }

    @GET
    public ArtistRankingResponse artistRanking(@QueryParam("orderBy") String orderBy) {
        List<String> artistsRanked = artistRankingService.orderBy(ranking.get(orderBy));
        return new ArtistRankingResponse(artistsRanked);
    }
}