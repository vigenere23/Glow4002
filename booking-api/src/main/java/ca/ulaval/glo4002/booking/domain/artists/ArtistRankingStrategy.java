package ca.ulaval.glo4002.booking.domain.artists;

import java.util.List;

public interface ArtistRankingStrategy {
    public List<Artist> getOrderedArtists(List<Artist> artistsToOrder);
}
