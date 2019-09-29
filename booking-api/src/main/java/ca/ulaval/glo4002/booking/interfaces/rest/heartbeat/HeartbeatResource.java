package ca.ulaval.glo4002.booking.interfaces.rest.heartbeat;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;

@Path("/heartbeat")
@Produces(MediaType.APPLICATION_JSON)
public class HeartbeatResource {
	
	private Repository repository;

	@Inject
	public HeartbeatResource(Repository repository) {
		this.repository = repository;
	}
	
    @GET
    public HeartbeatResponse heartbeat(@QueryParam("token") String token) {
        return new HeartbeatResponse(token, repository);
    }

    @POST
    public Response blabla(HeartbeatResponse r) {
        return Response.ok().entity(r).build();
    }
}
