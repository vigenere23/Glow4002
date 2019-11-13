package ca.ulaval.glo4002.booking.domain.artists;

import java.util.Comparator;
import java.util.List;

public class DecreasingPriceOrderedArtists extends ArtistRanking {

    public List<String> getOrderedArtists(List<ArtistRankingInformation> artistsToOrder) {
        artistsToOrder.sort(Comparator.comparingDouble(ArtistRankingInformation::getPrice)
                .thenComparing(ArtistRankingInformation::getPopularity).reversed());
        return extractArtistsName(artistsToOrder);
    }
}
