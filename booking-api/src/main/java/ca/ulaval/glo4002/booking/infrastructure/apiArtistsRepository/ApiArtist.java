package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import java.util.List;

import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistDto;


public interface ApiArtist {

    List<ArtistDto> getArtistsDto();
}