package ca.ulaval.glo4002.booking.api.resources;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            departures = shuttleMapper.getShuttlesDto(transportUseCase.getAllDepartures());
            arrivals = shuttleMapper.getShuttlesDto(transportUseCase.getAllArrivals());
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            LocalDate date = LocalDate.parse(stringDate, formatter);
            departures = shuttleMapper.getShuttlesDto(transportUseCase.getShuttlesDepartureByDate(date));
            arrivals = shuttleMapper.getShuttlesDto(transportUseCase.getShuttlesArrivalByDate(date));
        }    
        return new TransportResponse(departures, arrivals);
    }
}
