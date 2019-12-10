package ca.ulaval.glo4002.booking.application.artists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.application.artists.dtos.ArtistDto;
import ca.ulaval.glo4002.booking.application.artists.dtos.ArtistDtoMapper;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.artists.ArtistSorterFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistSortingStrategy;
import ca.ulaval.glo4002.booking.domain.artists.ArtistSortingType;

@ExtendWith(MockitoExtension.class)
public class ArtistSortingUseCaseTest {

    final static ArtistSortingType A_SORTING_TYPE = ArtistSortingType.LOW_COSTS;
    
    @Mock List<Artist> artistsToOrder;
    @Mock List<Artist> orderedArtists;
    @Mock List<ArtistDto> sortedArtistDtos;
    @Mock ArtistRepository artistRepository;
    @Mock ArtistSorterFactory artistSortingFactory;
    @Mock ArtistSortingStrategy artistSortingStrategy;
    @Mock ArtistDtoMapper artistDtoMapper;
    @InjectMocks ArtistSortingUseCase artistSortingUseCase;

    @BeforeEach
    public void setUp() {
        when(artistRepository.findAll()).thenReturn(artistsToOrder);
        when(artistSortingFactory.create(A_SORTING_TYPE)).thenReturn(artistSortingStrategy);
        when(artistSortingStrategy.getSortedArtists(artistsToOrder)).thenReturn(orderedArtists);
        when(artistDtoMapper.toDtos(orderedArtists)).thenReturn(sortedArtistDtos);
    }

    @Test
    public void whenGettingSortedArtists_itReturnsSortedArtists() {
        assertThat(artistSortingUseCase.getOrderedArtists(A_SORTING_TYPE)).isEqualTo(sortedArtistDtos);
    }
}
