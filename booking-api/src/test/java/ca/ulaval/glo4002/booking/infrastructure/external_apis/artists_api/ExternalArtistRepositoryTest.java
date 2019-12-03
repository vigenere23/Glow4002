package ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.exceptions.ItemNotFound;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistDto;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistDtoMapper;

@ExtendWith(MockitoExtension.class)
public class ExternalArtistRepositoryTest {

    private static final String ARTIST_NAME = "Some artist";
    
    private List<Artist> artists;

    @Mock List<ArtistDto> artistsDtos;
    @Mock ArtistDtoMapper artistDtoMapper;
    @Mock ArtistApiJerseyClient client;
    @InjectMocks ExternalApiArtistRepository externalArtistRepository;

    @BeforeEach
    public void setup() {
        artists = new ArrayList<>();
    }

    @Test
    public void whenFindingAllArtists_itReturnsTheListOfArtistsFromJerseyClient() {
        when(client.getAll()).thenReturn(artistsDtos);
        when(artistDtoMapper.fromDtos(artistsDtos)).thenReturn(artists);

        List<Artist> returnedArtists = externalArtistRepository.findAll();
        assertThat(returnedArtists).isEqualTo(artists);
    }

    @Test
    public void givenNonExistantArtist_whenFindingArtistByName_itThrowsAnItemNotFoundException() {
        when(client.getAll()).thenReturn(artistsDtos);
        when(artistDtoMapper.fromDtos(artistsDtos)).thenReturn(new ArrayList<>());

        assertThatExceptionOfType(ItemNotFound.class).isThrownBy(() -> {
            externalArtistRepository.findByName("non-existant name");
        });
    }

    @Test
    public void givenExistantArtist_whenFindingArtistByName_itReturnsTheArtistWithTheSameName() {
        Artist existantArtist = addArtist(ARTIST_NAME);
        when(client.getAll()).thenReturn(artistsDtos);
        when(artistDtoMapper.fromDtos(artistsDtos)).thenReturn(artists);

        Artist returnedArtist = externalArtistRepository.findByName(ARTIST_NAME);

        assertThat(returnedArtist).isEqualTo(existantArtist);
    }

    private Artist addArtist(String artistName) {
        Artist artist = mock(Artist.class);
        when(artist.getName()).thenReturn(artistName);
        artists.add(artist);
        return artist;
    }
}
