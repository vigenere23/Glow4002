package ca.ulaval.glo4002.booking.domain.artists;

import java.util.List;

public interface ArtistSortingStrategy {
    List<Artist> getSortedArtists(List<Artist> artistsToOrder);
}
