package ca.ulaval.glo4002.booking.domain.artists;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRanking;
import ca.ulaval.glo4002.booking.domain.artists.Ranking;

public class ArtistRankingService {
    
    private ArtistRanking artistRanking = new ArtistRanking();

	public List<String> orderBy(Ranking rankingType) {
        List<String> orderedArtistNames;
		if (rankingType == Ranking.LOW_COSTS) {
            orderedArtistNames = artistRanking.getDecreasingPriceOrderedArtists();
        } else {
            orderedArtistNames = artistRanking.getAscendingPopularityOrderedArtists();
        }
        return orderedArtistNames;
	}

}
