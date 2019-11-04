package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import java.util.List;

import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistDto;

public interface ApiArtist {

    List<ArtistDto> getArtistsDto();
}