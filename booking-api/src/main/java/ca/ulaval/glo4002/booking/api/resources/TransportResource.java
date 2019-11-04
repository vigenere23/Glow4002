package ca.ulaval.glo4002.booking.api.resources;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.api.dtoMappers.ShuttleMapper;
import ca.ulaval.glo4002.booking.api.dtos.transport.ShuttleDto;
import ca.ulaval.glo4002.booking.api.dtos.transport.TransportResponse;
import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.domain.transport.TransportExposer;

@Path("/shuttle-manifests")
@Produces(MediaType.APPLICATION_JSON)
public class TransportResource {
    
    private TransportExposer transportExposer;
    private ShuttleMapper shuttleMapper;
    
    @Inject
    public TransportResource(TransportExposer transportExposer) {
        this.transportExposer = transportExposer;
        shuttleMapper = new ShuttleMapper();
    }

    @GET
    public TransportResponse transport(@QueryParam("date") String stringDate) throws InvalidFormatException {
        List<ShuttleDto> departures;
        List<ShuttleDto> arrivals;
        if (stringDate == null) {
            departures = shuttleMapper.getShuttlesDto(transportExposer.getAllDepartures());
            arrivals = shuttleMapper.getShuttlesDto(transportExposer.getAllArrivals());
        } else {
            LocalDate date = LocalDate.parse(stringDate);
            departures = shuttleMapper.getShuttlesDto(transportExposer.getShuttlesDepartureByDate(date));
            arrivals = shuttleMapper.getShuttlesDto(transportExposer.getShuttlesArrivalByDate(date));
        }    
        return new TransportResponse(departures, arrivals);
    }
}
