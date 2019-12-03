package ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api;

import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.exceptions.ItemNotFound;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistDtoMapper;

import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

public class ExternalApiArtistRepository implements ArtistRepository {

    @Inject private ArtistDtoMapper artistDtoMapper;
    @Inject private ArtistApiJerseyClient client;

    @Override
    public List<Artist> findAll() {
        return artistDtoMapper.fromDtos(client.getAll());
    }

    @Override
    public Artist findByName(String name) {
        try {
            return findAll()
                .stream()
                .filter(artist -> artist.getName().equals(name))
                .findFirst()
                .get();
        }
        catch (NoSuchElementException exception) {
            throw new ItemNotFound("artist");
        }
    }
}
