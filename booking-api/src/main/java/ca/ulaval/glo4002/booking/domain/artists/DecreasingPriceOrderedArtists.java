package ca.ulaval.glo4002.booking.domain.artists;

import java.util.Comparator;
import java.util.List;

public class DecreasingPriceOrderedArtists extends ArtistRanking {

    public List<String> getOrderedArtits(List<ArtistRankingInformation> artistsToOrder) {
        artistsToOrder.sort(Comparator.comparingDouble(ArtistRankingInformation::getPrice).reversed()
                .thenComparing(ArtistRankingInformation::getPopularity));
        return extractArtistsName(artistsToOrder);
    }
}
