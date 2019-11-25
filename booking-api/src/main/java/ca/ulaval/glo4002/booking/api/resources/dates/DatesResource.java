package ca.ulaval.glo4002.booking.api.resources.dates;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.api.resources.dates.requests.UpdateDatesRequest;
import ca.ulaval.glo4002.booking.application.dates.DatesUseCase;

@Path("/configuration")
@Produces(MediaType.APPLICATION_JSON)
public class DatesResource {

    private DatesUseCase datesUseCase;

    @Inject
    public DatesResource(DatesUseCase datesUseCase) {
        this.datesUseCase = datesUseCase;
    }

    @POST
    public Response updateDates(UpdateDatesRequest request) {
        datesUseCase.updateFestivalDates(request.startDate, request.endDate);
        return Response.ok().build();
    }
}
