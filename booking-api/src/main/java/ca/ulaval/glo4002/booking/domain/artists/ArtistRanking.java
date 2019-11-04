package ca.ulaval.glo4002.booking.domain.artists;

import java.util.ArrayList;
import java.util.List;

public class ArtistRanking {

    private List<String> decreasingPriceOrderedArtists;
    private List<String> ascendingPopularityOrderedArtists;

    public ArtistRanking() {
        decreasingPriceOrderedArtists = new ArrayList<String>();
        ascendingPopularityOrderedArtists = new ArrayList<String>();
    }

    public List<String> getDecreasingPriceOrderedArtists(List<ArtistRankingInformation> artistsToOrder) {

        decreasingPriceOrderedArtists.clear();


        //todo: add logique pour classer les artistes comme il le faut (issue #132)
        return decreasingPriceOrderedArtists;
    }

    public List<String> getAscendingPopularityOrderedArtists(List<ArtistRankingInformation> artistsToOrder) {

        ascendingPopularityOrderedArtists.clear();


        //todo: add logique pour classer les artistes comme il le faut (issue #132)
        return decreasingPriceOrderedArtists;
    }
}