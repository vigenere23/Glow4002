package ca.ulaval.glo4002.booking.api.resources.program;

import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.api.resources.program.dto.ProgramMapper;
import ca.ulaval.glo4002.booking.application.ProgramUseCase;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;

@Path("/program")
@Produces(MediaType.APPLICATION_JSON)
public class ProgramResource {

    private ProgramMapper programMapper;
    private ProgramUseCase programUseCase;
    private ProgramValidator programValidator;

    @Inject
    public ProgramResource(ProgramUseCase programUseCase, ProgramValidator programValidator) {
        this.programUseCase = programUseCase;
        this.programValidator = programValidator;
        programMapper = new ProgramMapper();
    }

    @POST
    public Response create(ProgramRequest request) throws URISyntaxException {
        programUseCase.provideProgramResources(programMapper.fromDto(request, programValidator));
        return Response.ok().build();
    }
}
