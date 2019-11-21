package ca.ulaval.glo4002.booking.application.transport.dtos;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.transport.Shuttle;

public class ShuttleDtoMapper {

    public List<ShuttleDto> toDtos(List<Shuttle> shuttles) {
        return shuttles
            .stream()
            .map(shuttle -> toDto(shuttle))
            .collect(Collectors.toList());
    }

    public ShuttleDto toDto(Shuttle shuttle) {
        return new ShuttleDto(shuttle.getDate(), shuttle.getCategory(), shuttle.getPassengerNumbers());
    }
}
