package ca.ulaval.glo4002.booking.interfaces.rest.responses;
import ca.ulaval.glo4002.booking.interfaces.rest.dtoMappers.ShuttleDto;

import java.util.LinkedList;
import java.util.List;

public class TransportResponse {
    
    public List<ShuttleDto> departures = new LinkedList<>();
    public List<ShuttleDto> arrivals = new LinkedList<>();

    public TransportResponse(List<ShuttleDto> departureShuttleDtos, List<ShuttleDto> arrivalShuttleDtos) {
        this.departures = departureShuttleDtos;
        this.arrivals = arrivalShuttleDtos;
    }
}
