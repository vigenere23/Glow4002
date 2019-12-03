package ca.ulaval.glo4002.booking.domain.artists;

import java.util.List;

public interface ArtistRankingStrategy {
    List<Artist> getOrderedArtists(List<Artist> artistsToOrder);
}
