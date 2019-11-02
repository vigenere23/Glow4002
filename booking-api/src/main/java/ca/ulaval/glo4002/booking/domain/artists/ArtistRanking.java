package ca.ulaval.glo4002.booking.domain.artists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArtistRanking {

    public ArtistRanking() {}

    public List<String> getDecreasingPriceOrderedArtists(List<ArtistRankingInformation> artistsToOrder) {
        Collections.sort(artistsToOrder, Comparator.comparingDouble(ArtistRankingInformation::getPrice).reversed()
        .thenComparing(ArtistRankingInformation::getPopularity));
        return extractArtistsName(artistsToOrder);
    }

    public List<String> getAscendingPopularityOrderedArtists(List<ArtistRankingInformation> artistsToOrder) {
        Collections.sort(artistsToOrder, Comparator.comparingDouble(ArtistRankingInformation::getPopularity));
        return extractArtistsName(artistsToOrder);
    }

    private List<String> extractArtistsName(List<ArtistRankingInformation> orderedArtists) {
            List<String> orderedArtistsName = new ArrayList<String>();
            for (ArtistRankingInformation artist : orderedArtists) {
                orderedArtistsName.add(artist.getArtistName());
            }
            return orderedArtistsName;
    }
}
