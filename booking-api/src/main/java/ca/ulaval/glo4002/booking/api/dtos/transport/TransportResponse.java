package ca.ulaval.glo4002.booking.api.dtos.transport;

import java.util.List;

public class TransportResponse {
    
    public List<ShuttleDto> departures;
    public List<ShuttleDto> arrivals;

    public TransportResponse(List<ShuttleDto> departureShuttleDtos, List<ShuttleDto> arrivalShuttleDtos) {
        this.departures = departureShuttleDtos;
        this.arrivals = arrivalShuttleDtos;
    }
}
