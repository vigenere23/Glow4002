package ca.ulaval.glo4002.booking.api.resources.transport.responses;

import java.util.List;

import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDto;

public class TransportResponse {
    
    public final List<ShuttleDto> departures;
    public final List<ShuttleDto> arrivals;

    public TransportResponse(List<ShuttleDto> departureShuttles, List<ShuttleDto> arrivalShuttles) {
        this.departures = departureShuttles;
        this.arrivals = arrivalShuttles;
    }
}
