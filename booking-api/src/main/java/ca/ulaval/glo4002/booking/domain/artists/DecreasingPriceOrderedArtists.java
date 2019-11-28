package ca.ulaval.glo4002.booking.domain.artists;

import java.util.Comparator;
import java.util.List;

public class DecreasingPriceOrderedArtists implements ArtistRankingStrategy {

    public List<Artist> getOrderedArtists(List<Artist> artistsToOrder) {
        artistsToOrder.sort(Comparator
            .comparing(Artist::getPrice)
            .thenComparing(Artist::getPopularity)
            .reversed()
        );
        return artistsToOrder;
    }
}
