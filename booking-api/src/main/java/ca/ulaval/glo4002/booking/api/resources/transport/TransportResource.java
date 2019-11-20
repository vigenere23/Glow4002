package ca.ulaval.glo4002.booking.api.resources.transport;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.api.resources.transport.responses.TransportResponse;
import ca.ulaval.glo4002.booking.application.transport.TransportUseCase;
import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDto;

@Path("/shuttle-manifests")
@Produces(MediaType.APPLICATION_JSON)
public class TransportResource {
    
    private TransportUseCase transportUseCase;
    
    @Inject
    public TransportResource(TransportUseCase transportUseCase) {
        this.transportUseCase = transportUseCase;
    }

    @GET
    public Response transport(@QueryParam("date") String stringDate) {
        TransportResponse response = stringDate == null
            ? getTransportResponse()
            : getTransportResponseFilteredByDate(stringDate);

        return Response.ok(response).build();
    }

    private TransportResponse getTransportResponse() {
        List<ShuttleDto> departures = transportUseCase.getAllDepartures();
        List<ShuttleDto> arrivals = transportUseCase.getAllArrivals();
        return new TransportResponse(departures, arrivals);
    }

    private TransportResponse getTransportResponseFilteredByDate(String stringDate) {
        LocalDate date = LocalDate.parse(stringDate);
        List<ShuttleDto> departures = transportUseCase.getShuttlesDepartureByDate(date);
        List<ShuttleDto> arrivals = transportUseCase.getShuttlesArrivalByDate(date);
        return new TransportResponse(departures, arrivals);
    }
}
