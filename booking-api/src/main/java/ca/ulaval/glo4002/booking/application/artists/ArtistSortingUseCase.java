package ca.ulaval.glo4002.booking.application.artists;

import java.util.List;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.application.artists.dtos.ArtistDto;
import ca.ulaval.glo4002.booking.application.artists.dtos.ArtistDtoMapper;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.artists.ArtistSorterFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistSortingType;

public class ArtistSortingUseCase {

    @Inject private ArtistRepository artistsRepository;
    @Inject private ArtistSorterFactory artistSortingFactory;
    @Inject private ArtistDtoMapper artistDtoMapper;

    public List<ArtistDto> getOrderedArtists(ArtistSortingType artistSortingType) {
        List<Artist> artistsToOrder = artistsRepository.findAll();
        List<Artist> sortedArtists = artistSortingFactory.create(artistSortingType).getSortedArtists(artistsToOrder);
        return artistDtoMapper.toDtos(sortedArtists);
    }
}
