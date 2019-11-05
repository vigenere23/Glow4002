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
import ca.ulaval.glo4002.booking.domain.application.ProgramResourcesProvider;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;

@Path("/program")
@Produces(MediaType.APPLICATION_JSON)
public class ProgramResource {

    private ProgramMapper programMapper;

    public ProgramResource () {
        programMapper = new ProgramMapper();
    }

    private ProgramResourcesProvider programResourcesProvider;
    private FestivalDates glow4002Dates;

    @Inject
    public ProgramResource(ProgramResourcesProvider programResourcesProvider, FestivalDates glow4002Dates) {
        this.programResourcesProvider = programResourcesProvider;
        this.glow4002Dates = glow4002Dates;
    }

    @POST
    public Response create(ProgramRequest request) throws URISyntaxException {
        programResourcesProvider.provideProgramResources(programMapper.fromDto(request, glow4002Dates));
        return Response.status(200).contentLocation(new URI("/")).build();
    }
}