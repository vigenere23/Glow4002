package ca.ulaval.glo4002.booking.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.transport.TransportExposer;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.ShuttleDto;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.TransportResponse;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidEventDateException;
import ca.ulaval.glo4002.booking.interfaces.rest.dtoMappers.ShuttleMapper;

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
    public TransportResponse transport(@QueryParam("date") String stringDate) throws InvalidEventDateException {
        List<ShuttleDto> departures = new LinkedList<ShuttleDto>();
        List<ShuttleDto> arrivals = new LinkedList<ShuttleDto>();
        if (stringDate == null) {
            departures = shuttleMapper.getShuttlesDto(transportExposer.getAllDepartures());
            arrivals = shuttleMapper.getShuttlesDto(transportExposer.getAllArrivals());
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            LocalDate date = LocalDate.parse(stringDate, formatter);
            try {
                departures = shuttleMapper.getShuttlesDto(transportExposer.getShuttlesDepartureByDate(date));
                arrivals = shuttleMapper.getShuttlesDto(transportExposer.getShuttlesArrivalByDate(date));
            }
            catch (OutOfFestivalDatesException exception) {
                throw new InvalidEventDateException(exception.getMessage());
            }
        }
        return new TransportResponse(departures, arrivals);
    }
}
