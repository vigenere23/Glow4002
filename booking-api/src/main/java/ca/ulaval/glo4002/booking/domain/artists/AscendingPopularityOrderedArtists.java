package ca.ulaval.glo4002.booking.domain.artists;

import java.util.Comparator;
import java.util.List;

public class AscendingPopularityOrderedArtists implements ArtistRankingStrategy {

    @Override
    public List<Artist> getOrderedArtists(List<Artist> artistsToOrder) {
        artistsToOrder.sort(Comparator.comparingDouble(Artist::getPopularity));
        return artistsToOrder;
    }
}
