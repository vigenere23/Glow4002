package ca.ulaval.glo4002.booking.domain.artists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArtistRanking {

    private List<String> decreasingPriceOrderedArtists;
    private List<String> ascendingPopularityOrderedArtists;
    private List<ArtistRankingInformation> artistsToOrder;

    public ArtistRanking() {
        decreasingPriceOrderedArtists = new ArrayList<String>();
        ascendingPopularityOrderedArtists = new ArrayList<String>();
        artistsToOrder = new ArrayList<ArtistRankingInformation>();
    } 

    public List<String> getDecreasingPriceOrderedArtists() {
        decreasingPriceOrderedArtists.clear();
        artistsToOrder.stream().sorted().collect(Collectors.toList());

        
        return decreasingPriceOrderedArtists;
    }

    public List<String> getAscendingPopularityOrderedArtists() {

        ascendingPopularityOrderedArtists.clear();


        //todo: add logique pour classer les artistes comme il le faut
        return decreasingPriceOrderedArtists;
    }
}
