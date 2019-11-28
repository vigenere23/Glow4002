package ca.ulaval.glo4002.booking.application.transport.dtos;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.transport2.Shuttle;

public class ShuttleDtoMapper2 {

    public List<ShuttleDto2> toDtos(List<Shuttle> shuttles) {
        return shuttles
            .stream()
            .map(shuttle -> toDto(shuttle))
            .collect(Collectors.toList());
    }

    public ShuttleDto2 toDto(Shuttle shuttle) {
        return new ShuttleDto2(shuttle.getDate(), shuttle.getCategory(), shuttle.getPassengerNumbers());
    }
}
