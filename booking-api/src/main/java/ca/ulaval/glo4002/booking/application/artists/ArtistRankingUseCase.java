package ca.ulaval.glo4002.booking.application.artists;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.artists.Ranking;

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
