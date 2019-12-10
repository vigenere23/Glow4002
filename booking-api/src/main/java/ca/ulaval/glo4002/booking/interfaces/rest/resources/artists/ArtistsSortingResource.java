package ca.ulaval.glo4002.booking.interfaces.rest.resources.artists;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.artists.responses.ArtistSortingResponse;
import ca.ulaval.glo4002.booking.application.artists.ArtistSortingUseCase;
import ca.ulaval.glo4002.booking.application.artists.dtos.ArtistDto;
import ca.ulaval.glo4002.booking.domain.artists.ArtistSortingType;

@Path("/program/artists")
@Produces(MediaType.APPLICATION_JSON)
public class ArtistsSortingResource {
    
    @Inject private ArtistSortingUseCase artistSortingUseCase;

    @GET
    public Response artistsorting(@QueryParam("orderBy") ArtistSortingType artistSortingType) {
        List<ArtistDto> sortedArtists = artistSortingUseCase.getOrderedArtists(artistSortingType);
        
        ArtistSortingResponse response = new ArtistSortingResponse(sortedArtists);
        return Response.ok().entity(response).build();
    }
}
