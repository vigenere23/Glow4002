package ca.ulaval.glo4002.booking.application.artists.dtos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;

public class ArtistDtoMapperTest {

    private Artist artist;
    private ArtistDtoMapper artistDtoMapper;

    @BeforeEach
    public void setup() {
        artistDtoMapper = new ArtistDtoMapper();
        artist = createArtist();
    }

    @Test
    public void whenMappingToDto_itSetsTheSameArtistName() {
        ArtistDto artistDto = artistDtoMapper.toDto(artist);
        assertThat(artistDto.name).isEqualTo(artist.getName());
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePopularity() {
        ArtistDto artistDto = artistDtoMapper.toDto(artist);
        assertThat(artistDto.popularity).isEqualTo(artist.getPopularity());
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePriceRounded() {
        ArtistDto artistDto = artistDtoMapper.toDto(artist);
        assertThat(artistDto.price).isEqualTo(artist.getPrice().getRoundedAmountFromCurrencyScale());
    }

    @Test
    public void whenMappingToDto_itSetsTheSameGroupSize() {
        ArtistDto artistDto = artistDtoMapper.toDto(artist);
        assertThat(artistDto.groupSize).isEqualTo(artist.getGroupSize());
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePassengerNumberValue() {
        ArtistDto artistDto = artistDtoMapper.toDto(artist);
        assertThat(artistDto.passengerNumber).isEqualTo(artist.getPassengerNumber().getValue());
    }

    private Artist createArtist() {
        return new Artist("John Pecker", 5, new Price(1234.4567), 9, new PassengerNumber(10));
    }
}
