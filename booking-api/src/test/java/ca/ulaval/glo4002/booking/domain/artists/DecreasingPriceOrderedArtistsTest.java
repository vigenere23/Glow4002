package ca.ulaval.glo4002.booking.domain.artists;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;

public class DecreasingPriceOrderedArtistsTest {

    private List<ArtistRankingInformation> artistsToOrder = new ArrayList<>();
    private DecreasingPriceOrderedArtists decreasingPriceOrderedArtists = new DecreasingPriceOrderedArtists();

    @Test
    public void givenArtistsToOrderWithDifferentPrices_whenGetOrderedArtists_thenOrderArtistsNameByPrice() {
        fillArtistsToOrderWithDifferentPrice();

        List<String> orderedArtists = decreasingPriceOrderedArtists.getOrderedArtists(artistsToOrder);

        assertEquals("mostExpensiveArtist", orderedArtists.get(0));
        assertEquals("lessExpensiveArtist", orderedArtists.get(2));
    }

    private void fillArtistsToOrderWithDifferentPrice() {
        addArtist(mock(ArtistRankingInformation.class), 5000f, 1, "mostExpensiveArtist");
        addArtist(mock(ArtistRankingInformation.class), 3000f, 3, "lessExpensiveArtist");
        addArtist(mock(ArtistRankingInformation.class), 4000f, 2, "secondExpensiveArtist");
    }

    @Test
    public void givenArtistsToOrderWithSamePrices_whenGetOrderedArtists_thenOrderArtistsNameByPriceAndPopularity() {
        fillArtistsToOrderWithSamePrice();

        List<String> orderedArtists = decreasingPriceOrderedArtists.getOrderedArtists(artistsToOrder);

        assertEquals("mostExpensiveMostPopularArtist", orderedArtists.get(0));
        assertEquals("lessExpensiveArtist", orderedArtists.get(2));
    }

    private void fillArtistsToOrderWithSamePrice() {
        addArtist(mock(ArtistRankingInformation.class), 5000f, 2, "mostExpensiveLessPopularArtist");
        addArtist(mock(ArtistRankingInformation.class), 3000f, 3, "lessExpensiveArtist");
        addArtist(mock(ArtistRankingInformation.class), 5000f, 1, "mostExpensiveMostPopularArtist");
    }

    private void addArtist(ArtistRankingInformation artist, float price, int popularity, String name) {
        when(artist.getPrice()).thenReturn(price);
        when(artist.getPopularity()).thenReturn(popularity);
        when(artist.getArtistName()).thenReturn(name);
        artistsToOrder.add(artist);
    }
}