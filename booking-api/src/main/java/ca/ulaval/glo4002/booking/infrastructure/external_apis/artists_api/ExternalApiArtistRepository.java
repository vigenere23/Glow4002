package ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api;

import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistDtoMapper;

import java.util.List;

import javax.inject.Inject;

public class ExternalApiArtistRepository implements ArtistRepository {

    private ArtistDtoMapper artistDtoMapper;
    private ApiArtist apiArtist;

    @Inject
    public ExternalApiArtistRepository(ArtistDtoMapper artistDtoMapper, ApiArtist apiArtist) {
        this.artistDtoMapper = artistDtoMapper;
        this.apiArtist = apiArtist;
    }

    @Override
    public List<Artist> findAll() {
        return artistDtoMapper.fromDtos(apiArtist.getAll());
    }

    @Override
    public Artist findByName(String name) {
        return findAll()
            .stream()
            .filter(artistInformation -> artistInformation.getName().equals(name))
            .findFirst()
            .orElse(null);
    }
}
