package ca.ulaval.glo4002.booking.interfaces.rest.resources.artists;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.artists.responses.ArtistRankingResponse;
import ca.ulaval.glo4002.booking.application.artists.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.artists.Ranking;

@Path("/program/artists")
@Produces(MediaType.APPLICATION_JSON)
public class ArtistsRankingResource {

    private static final HashMap<String, Ranking> ranking = new HashMap<String, Ranking>() {{
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
            throw new InvalidFormatException();
        }
        
        List<String> artistsRanked = artistRankingUseCase
            .orderBy(ranking.get(orderBy))
            .stream()
            .map(Artist::getName)
            .collect(Collectors.toList());
        
        ArtistRankingResponse response = new ArtistRankingResponse(artistsRanked);
        return Response.ok().entity(response).build();
    }
}
