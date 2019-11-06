package ca.ulaval.glo4002.booking.api.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.api.dtoMappers.ProgramMapper;
import ca.ulaval.glo4002.booking.api.dtos.program.ProgramRequest;
import ca.ulaval.glo4002.booking.application.ProgramUseCase;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;

@Path("/program")
@Produces(MediaType.APPLICATION_JSON)
public class ProgramResource {

    private ProgramMapper programMapper;
    private ProgramUseCase programUseCase;
    private FestivalDates glow4002Dates;

    @Inject
    public ProgramResource(ProgramUseCase programUseCase, FestivalDates glow4002Dates) {
        this.glow4002Dates = glow4002Dates;
        this.programUseCase = programUseCase;
        programMapper = new ProgramMapper();
    }

    @POST
    public Response create(ProgramRequest request) throws URISyntaxException {
        programUseCase.provideProgramResources(programMapper.fromDto(request, glow4002Dates));
        return Response.status(200).contentLocation(new URI("/")).build();
    }
}