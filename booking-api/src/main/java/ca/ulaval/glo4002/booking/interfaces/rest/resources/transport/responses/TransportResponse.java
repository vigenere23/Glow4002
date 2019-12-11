package ca.ulaval.glo4002.booking.interfaces.rest.resources.transport.responses;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDto;

public class TransportResponse {
    
    public final List<ShuttleResponse> departures;
    public final List<ShuttleResponse> arrivals;

    public TransportResponse(List<ShuttleDto> departureShuttles, List<ShuttleDto> arrivalShuttles) {
        departures = departureShuttles
            .stream()
            .map(shuttleDto -> new ShuttleResponse(shuttleDto))
            .collect(Collectors.toList());
        arrivals = arrivalShuttles
            .stream()
            .map(shuttleDto -> new ShuttleResponse(shuttleDto))
            .collect(Collectors.toList());
    }
}
