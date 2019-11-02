package ca.ulaval.glo4002.booking.api.resources;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.api.dtos.artist.ArtistRankingResponse;
import ca.ulaval.glo4002.booking.domain.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.artists.Ranking;

@Path("/program/artists")
@Produces(MediaType.APPLICATION_JSON)
public class ArtistsRankingResource {

    private ArtistRankingUseCase artistRankingUseCase;
    private  static final HashMap<String, Ranking> ranking = new HashMap<String, Ranking>() {{
        put("lowCosts", Ranking.LOW_COSTS);
        put("mostPopular", Ranking.MOST_POPULARITY);
    }};
    
    @Inject
    public ArtistsRankingResource(ArtistRankingUseCase artistRankingUseCase) {
        this.artistRankingUseCase = artistRankingUseCase;
    }

    @GET
    public ArtistRankingResponse artistRanking(@QueryParam("orderBy") String orderBy) {
        List<String> artistsRanked = artistRankingUseCase.orderBy(ranking.get(orderBy));
        return new ArtistRankingResponse(artistsRanked);
    }
}