package ca.ulaval.glo4002.booking.domain.artists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;

public class DecreasingPriceOrderedArtistsTest {

    private List<Artist> artistsToOrder = new ArrayList<>();
    private DecreasingPriceSorter decreasingPriceSorter = new DecreasingPriceSorter();

    @Test
    public void givenArtistsToOrderWithDifferentPrices_whenGetOrderedArtists_thenOrderArtistsNameByPrice() {
        fillArtistsToOrderWithDifferentPrice();

        List<Artist> orderedArtists = decreasingPriceSorter.getSortedArtists(artistsToOrder);

        assertEquals("mostExpensiveArtist", orderedArtists.get(0).getName());
        assertEquals("lessExpensiveArtist", orderedArtists.get(2).getName());
    }

    @Test
    public void givenArtistsToOrderWithSamePrices_whenGetOrderedArtists_thenOrderArtistsNameByPriceAndPopularity() {
        fillArtistsToOrderWithSamePrice();

        List<Artist> orderedArtists = decreasingPriceSorter.getSortedArtists(artistsToOrder);

        assertEquals("mostExpensiveMostPopularArtist", orderedArtists.get(0).getName());
        assertEquals("lessExpensiveArtist", orderedArtists.get(2).getName());
    }

    private void fillArtistsToOrderWithDifferentPrice() {
        addArtist(mock(Artist.class), new Price(5000), 1, "mostExpensiveArtist");
        addArtist(mock(Artist.class), new Price(3000), 3, "lessExpensiveArtist");
        addArtist(mock(Artist.class), new Price(4000), 2, "secondExpensiveArtist");
    }

    private void fillArtistsToOrderWithSamePrice() {
        addArtist(mock(Artist.class), new Price(5000), 2, "mostExpensiveMostPopularArtist");
        addArtist(mock(Artist.class), new Price(3000), 3, "lessExpensiveArtist");
        addArtist(mock(Artist.class), new Price(5000), 1, "mostExpensiveLessPopularArtist");
    }

    private void addArtist(Artist artist, Price price, int popularity, String name) {
        when(artist.getPrice()).thenReturn(price);
        when(artist.getPopularity()).thenReturn(popularity);
        when(artist.getName()).thenReturn(name);
        artistsToOrder.add(artist);
    }
}
