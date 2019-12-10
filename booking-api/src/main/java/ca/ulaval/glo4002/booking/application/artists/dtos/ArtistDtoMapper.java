package ca.ulaval.glo4002.booking.application.artists.dtos;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.artists.Artist;

public class ArtistDtoMapper {

    public List<ArtistDto> toDtos(List<Artist> artists) {
        return artists
            .stream()
            .map(artist -> toDto(artist))
            .collect(Collectors.toList());
    }

    public ArtistDto toDto(Artist artist) {
        return new ArtistDto(
            artist.getName(),
            artist.getPopularity(),
            artist.getPrice(),
            artist.getPassengerNumber(),
            artist.getGroupSize()
        );
    }
}
