package ca.ulaval.glo4002.booking.application.artists;

import java.util.List;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.artists.Ranking;

public class ArtistRankingUseCase {

    @Inject private ArtistRepository artistsRepository;
    @Inject private ArtistRankingFactory artistRankingFactory;

    public List<String> orderBy(Ranking rankingType) {
        List<ArtistRankingInformation> artistsToOrder = artistsRepository.findArtistRankingInformation();
        return artistRankingFactory.createArtistRanking(rankingType).getOrderedArtists(artistsToOrder);
    }
}
