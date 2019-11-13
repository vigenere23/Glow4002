package ca.ulaval.glo4002.booking.api.resources.artists;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.api.resources.exceptionMappers.dto.ClientErrorDto;
import ca.ulaval.glo4002.booking.api.resources.exceptionMappers.dto.ClientErrorResponseBuilder;
import ca.ulaval.glo4002.booking.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.artists.Ranking;

@Path("/program/artists")
@Produces(MediaType.APPLICATION_JSON)
public class ArtistsRankingResource {

    private  static final HashMap<String, Ranking> ranking = new HashMap<String, Ranking>() {{
        put("lowCosts", Ranking.LOW_COSTS);
        put("mostPopular", Ranking.MOST_POPULARITY);
    }};
    private ArtistRankingUseCase artistRankingUseCase;
    
    @Inject
    public ArtistsRankingResource(ArtistRankingUseCase artistRankingUseCase) {
        this.artistRankingUseCase = artistRankingUseCase;
    }

    @GET
    public Response artistRanking(@QueryParam("orderBy") String orderBy) {
        if(!ranking.containsKey(orderBy)){
            return new ClientErrorResponseBuilder(
                new ClientErrorDto("Bad request.", "Invalid query parameter.")).build();
        }
        List<String> artistsRanked = artistRankingUseCase.orderBy(ranking.get(orderBy));
        ArtistRankingResponse response = new ArtistRankingResponse();
        response.artists = artistsRanked;
        return Response.status(200).entity(response).build();
    }
}