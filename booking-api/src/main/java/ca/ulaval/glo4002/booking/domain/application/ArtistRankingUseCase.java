package ca.ulaval.glo4002.booking.domain.application;

import ca.ulaval.glo4002.booking.domain.artists.*;

import java.util.List;

public class ArtistRankingUseCase {

    private ArtistRepository artistsRepository;
    private ArtistRankingFactory artistRankingFactory;

    public ArtistRankingUseCase(ArtistRepository artistsRepository, ArtistRankingFactory artistRankingFactory) {
        this.artistsRepository = artistsRepository;
        this.artistRankingFactory = artistRankingFactory;
    }

    public List<String> orderBy(Ranking rankingType) {
        List<ArtistRankingInformation> artistsToOrder = artistsRepository.findArtistRankingInformation();
        return artistRankingFactory.createArtistRanking(rankingType).getOrderedArtists(artistsToOrder);
    }
}
