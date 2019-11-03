package ca.ulaval.glo4002.booking.api.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.api.dtos.program.ProgramRequest;
import ca.ulaval.glo4002.booking.domain.application.ProgramResourcesProvider;

@Path("/program")
@Produces(MediaType.APPLICATION_JSON)
public class ProgramResource {

    private ProgramResourcesProvider programResourcesProvider;

    @Inject
    public ProgramResource(ProgramResourcesProvider programResourcesProvider) {
        this.programResourcesProvider = programResourcesProvider;
    }

    @POST
    public Response create(ProgramRequest request) throws URISyntaxException {
        programResourcesProvider.provideProgramResources(request);
        return Response.status(200).contentLocation(new URI("/")).build();
    }
}