package ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;

public class ArtistDtoMapper {

    public List<Artist> fromDtos(List<ArtistDto> dtos) {
        return dtos
            .stream()
            .map(dto -> fromDto(dto))
            .collect(Collectors.toList());
    }

    public Artist fromDto(ArtistDto dto) {
        return new Artist(
            dto.name,
            dto.popularityRank,
            new Price(dto.price),
            dto.nbPeople,
            new PassengerNumber(dto.id)
        );
    }
}
