package ca.ulaval.glo4002.booking.interfaces.rest.resources.dates;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.application.dates.DatesUseCase;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.dates.requests.UpdateDatesRequest;

@Path("/configuration")
@Produces(MediaType.APPLICATION_JSON)
public class DatesResource {

    @Inject private DatesUseCase datesUseCase;

    @POST
    public Response updateDates(UpdateDatesRequest request) {
        datesUseCase.updateFestivalDates(request.startDate, request.endDate);
        return Response.ok().build();
    }
}
