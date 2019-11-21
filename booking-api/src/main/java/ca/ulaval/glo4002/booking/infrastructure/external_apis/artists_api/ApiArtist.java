package ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api;

import java.util.List;

import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistDto;

public interface ApiArtist {
    List<ArtistDto> getArtistsDto();
}
