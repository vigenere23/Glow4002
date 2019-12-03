package ca.ulaval.glo4002.booking.interfaces.rest.resources.artists;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.artists.responses.ArtistRankingResponse;
import ca.ulaval.glo4002.booking.application.artists.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRanking;

@Path("/program/artists")
@Produces(MediaType.APPLICATION_JSON)
public class ArtistsRankingResource {
    
    @Inject private ArtistRankingUseCase artistRankingUseCase;

    @GET
    public Response artistRanking(@QueryParam("orderBy") ArtistRanking ranking) {
        List<Artist> artistsRanked = artistRankingUseCase.orderBy(ranking);
        
        ArtistRankingResponse response = new ArtistRankingResponse(artistsRanked);
        return Response.ok().entity(response).build();
    }
}
