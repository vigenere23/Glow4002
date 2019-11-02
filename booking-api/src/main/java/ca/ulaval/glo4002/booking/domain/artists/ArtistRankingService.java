package ca.ulaval.glo4002.booking.domain.artists;

import java.util.List;

public class ArtistRankingService {

    private ArtistRanking artistRanking = new ArtistRanking();

	public List<String> orderBy(Ranking rankingType, List<ArtistRankingInformation> artistsToOrder) {
        List<String> orderedArtistNames;
		if (rankingType == Ranking.LOW_COSTS) {
            orderedArtistNames = artistRanking.getDecreasingPriceOrderedArtists(artistsToOrder);
        } else {
            orderedArtistNames = artistRanking.getAscendingPopularityOrderedArtists(artistsToOrder);
        }
        return orderedArtistNames;
	}
}
