package ca.ulaval.glo4002.booking.domain.artists;

import java.util.Comparator;
import java.util.List;

public class DecreasingPriceSorter implements ArtistSortingStrategy {

    @Override
    public List<Artist> getSortedArtists(List<Artist> artists) {
        artists.sort(Comparator
            .comparing(Artist::getPrice)
            .thenComparing(Artist::getPopularity)
            .reversed()
        );
        return artists;
    }
}
