package ca.ulaval.glo4002.booking.interfaces.rest.resources.program;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.application.program.ProgramUseCase;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramRequest;

@Path("/program")
@Produces(MediaType.APPLICATION_JSON)
public class ProgramResource {

    @Inject private ProgramUseCase programUseCase;

    @POST
    public Response create(ProgramRequest programRequest) {
        programUseCase.provideProgramResources(programRequest);
        return Response.ok().build();
    }
}
