package ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtoMappers;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistDto;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistDtoMapper;

public class ArtistDtoMapperTest {

    private final static int SOME_ARTIST_ID = 1;
    private final static String SOME_ARTIST_NAME = "Ginette Reno";
    private final static int SOME_NUMBER_OF_PEOPLE = 1;
    private final static String SOME_MUSIC_STYLE = "Pop";
    private final static Price SOME_PRICE = new Price(50000);
    private final static int SOME_POPULARITY_RANK = 1;
    private final static Object SOME_AVAILABILITY = new Object();

    private ArtistDtoMapper artistDtoMapper = new ArtistDtoMapper();
    private Artist artist;
    private ArtistDto artistDto;

    @BeforeEach
    public void setupArtistDtoMapper() {

        artistDto = new ArtistDto(SOME_ARTIST_ID, SOME_ARTIST_NAME, SOME_NUMBER_OF_PEOPLE, SOME_MUSIC_STYLE,
            SOME_PRICE.getRoundedAmountFromCurrencyScale(), SOME_POPULARITY_RANK, SOME_AVAILABILITY);
    }

    @Test
    public void whenMappingFromArtistDto_thenReturnEquivalentArtist() {
        artist = artistDtoMapper.fromDto(artistDto);
        
        assertEquals(SOME_ARTIST_NAME, artist.getName());
        assertEquals(SOME_ARTIST_ID, artist.getPassengerNumber().getValue());
        assertEquals(SOME_NUMBER_OF_PEOPLE, artist.getGroupSize());
        assertEquals(SOME_POPULARITY_RANK, artist.getPopularity());
        assertEquals(SOME_PRICE, artist.getPrice());
    }
}
