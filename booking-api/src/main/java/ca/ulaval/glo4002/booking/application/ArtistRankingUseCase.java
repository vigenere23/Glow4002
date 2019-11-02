package ca.ulaval.glo4002.booking.application;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingService;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.artists.Ranking;

import java.util.List;

public class ArtistRankingUseCase {

    private ArtistRepository artistsRepository;
    private ArtistRankingService artistRankingService;

    public ArtistRankingUseCase(ArtistRepository artistsRepository, ArtistRankingService artistRankingService) {
        this.artistsRepository = artistsRepository;
        this.artistRankingService = artistRankingService;
    }

    public List<String> orderBy(Ranking rankingType) {
        return artistRankingService.orderBy(rankingType, artistsRepository.findArtistRankingInformation());
    }
}
