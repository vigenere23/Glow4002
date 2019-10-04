package ca.ulaval.glo4002.booking.interfaces.rest.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptionMappers.OutOfFestivalDateException;
import ca.ulaval.glo4002.booking.interfaces.rest.responses.TransportResponse;

@Path("/shuttle-manifests")
@Produces(MediaType.APPLICATION_JSON)
public class TransportResource {
    
    private Glow4002 festival;
    
    @Inject
    public TransportResource(Glow4002 festival) {
        this.festival = festival;
    }

    @GET
    public TransportResponse transport(@QueryParam("date") String date) throws OutOfFestivalDateException {
        return new TransportResponse(date, festival);
    }
}
