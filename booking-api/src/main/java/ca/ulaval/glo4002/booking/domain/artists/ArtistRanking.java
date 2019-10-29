package ca.ulaval.glo4002.booking.domain.artists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArtistRanking {

    private List<ArtistRankingInformation> artistsToOrder;    

    public ArtistRanking() {
        artistsToOrder = new ArrayList<ArtistRankingInformation>();
    } 

    public List<String> getDecreasingPriceOrderedArtists() {
        Collections.sort(artistsToOrder, Comparator.comparingDouble(ArtistRankingInformation::getPrice)
        .thenComparing(ArtistRankingInformation::getPopularity).reversed());
        return extractArtistsName(artistsToOrder);
    }

    public List<String> getAscendingPopularityOrderedArtists() {
        Collections.sort(artistsToOrder, Comparator.comparingDouble(ArtistRankingInformation::getPrice));
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
