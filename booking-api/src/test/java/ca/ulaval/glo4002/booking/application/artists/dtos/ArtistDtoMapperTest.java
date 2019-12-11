package ca.ulaval.glo4002.booking.application.artists.dtos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;

public class ArtistDtoMapperTest {

    private final static String ARTIST_NAME = "John Pecker";
    private final static int POPULARITY = 5;
    private final static Price PRICE = new Price(1234.4567);
    private final static int GROUP_SIZE = 9;
    private final static PassengerNumber PASSENGER_NUMBER = new PassengerNumber(10);

    private Artist artist;
    private ArtistDtoMapper artistDtoMapper;

    @BeforeEach
    public void setup() {
        artistDtoMapper = new ArtistDtoMapper();
        artist = new Artist(ARTIST_NAME, POPULARITY, PRICE, GROUP_SIZE, PASSENGER_NUMBER);
    }

    @Test
    public void whenMappingToDto_itSetsTheSameArtistName() {
        ArtistDto artistDto = artistDtoMapper.toDto(artist);
        assertThat(artistDto.name).isEqualTo(ARTIST_NAME);
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePopularity() {
        ArtistDto artistDto = artistDtoMapper.toDto(artist);
        assertThat(artistDto.popularity).isEqualTo(POPULARITY);
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePriceRounded() {
        ArtistDto artistDto = artistDtoMapper.toDto(artist);
        assertThat(artistDto.price).isEqualTo(PRICE.getRoundedAmountFromCurrencyScale());
    }

    @Test
    public void whenMappingToDto_itSetsTheSameGroupSize() {
        ArtistDto artistDto = artistDtoMapper.toDto(artist);
        assertThat(artistDto.groupSize).isEqualTo(GROUP_SIZE);
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePassengerNumberValue() {
        ArtistDto artistDto = artistDtoMapper.toDto(artist);
        assertThat(artistDto.passengerNumber).isEqualTo(PASSENGER_NUMBER.getValue());
    }
}
