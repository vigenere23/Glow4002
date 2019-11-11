package ca.ulaval.glo4002.booking.api.resources.transport;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.api.resources.transport.dto.ShuttleDto;
import ca.ulaval.glo4002.booking.api.resources.transport.dto.ShuttleMapper;
import ca.ulaval.glo4002.booking.api.resources.transport.dto.TransportResponse;
import ca.ulaval.glo4002.booking.application.TransportUseCase;

@Path("/shuttle-manifests")
@Produces(MediaType.APPLICATION_JSON)
public class TransportResource {
    
    private TransportUseCase transportUseCase;
    private ShuttleMapper shuttleMapper;
    
    @Inject
    public TransportResource(TransportUseCase transportUseCase) {
        this.transportUseCase = transportUseCase;
        shuttleMapper = new ShuttleMapper();
    }

    @GET
    public TransportResponse transport(@QueryParam("date") String stringDate) throws InvalidFormatException {
        List<ShuttleDto> departures;
        List<ShuttleDto> arrivals;
        if (stringDate == null) {
            departures = shuttleMapper.toDto(transportUseCase.getAllDepartures());
            arrivals = shuttleMapper.toDto(transportUseCase.getAllArrivals());
        } else {
            LocalDate date = LocalDate.parse(stringDate);
            departures = shuttleMapper.toDto(transportUseCase.getShuttlesDepartureByDate(date));
            arrivals = shuttleMapper.toDto(transportUseCase.getShuttlesArrivalByDate(date));
        }    
        return new TransportResponse(departures, arrivals);
    }
}