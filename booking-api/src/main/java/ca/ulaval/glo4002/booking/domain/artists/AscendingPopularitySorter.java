package ca.ulaval.glo4002.booking.domain.artists;

import java.util.Comparator;
import java.util.List;

public class AscendingPopularitySorter implements ArtistSortingStrategy {

    @Override
    public List<Artist> getSortedArtists(List<Artist> artists) {
        artists.sort(Comparator.comparingDouble(Artist::getPopularity));
        return artists;
    }
}
