package ca.ulaval.glo4002.booking.api.resources.transport.dto;

import java.util.List;

public class TransportResponse {
    
    public List<ShuttleDto> departures;
    public List<ShuttleDto> arrivals;

    public TransportResponse(List<ShuttleDto> departureShuttleDto, List<ShuttleDto> arrivalShuttleDto) {
        this.departures = departureShuttleDto;
        this.arrivals = arrivalShuttleDto;
    }
}
