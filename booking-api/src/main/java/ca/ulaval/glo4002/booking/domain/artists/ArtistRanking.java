package ca.ulaval.glo4002.booking.domain.artists;

import java.util.ArrayList;
import java.util.List;

public abstract class ArtistRanking {

    public abstract List<String> getOrderedArtists(List<ArtistRankingInformation> artistsToOrder);

    protected List<String> extractArtistsName(List<ArtistRankingInformation> orderedArtists) {
            List<String> orderedArtistsName = new ArrayList<>();
            for (ArtistRankingInformation artist : orderedArtists) {
                orderedArtistsName.add(artist.getArtistName());
            }
            return orderedArtistsName;
    }
}
