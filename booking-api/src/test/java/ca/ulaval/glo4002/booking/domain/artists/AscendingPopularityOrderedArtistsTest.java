package ca.ulaval.glo4002.booking.domain.artists;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.artists.Artist;

public class AscendingPopularityOrderedArtistsTest {

    private List<Artist> artistsToOrder = new ArrayList<>();
    private AscendingPopularityOrderedArtists ascendingPopularityOrderedArtists = new AscendingPopularityOrderedArtists();

    @Test
    public void givenArtistsToOrder_whenGetOrderedArtists_thenOrderArtistsNameFromMostPopularToLessPopular() {
        fillArtistsToOrder();

        List<Artist> orderedArtists = ascendingPopularityOrderedArtists.getOrderedArtists(artistsToOrder);

        assertEquals("mostPopularArtist", orderedArtists.get(0).getName());
        assertEquals("lessPopularArtist", orderedArtists.get(2).getName());
    }

    private void fillArtistsToOrder() {
        addArtist(mock(Artist.class), 1, "mostPopularArtist");
        addArtist(mock(Artist.class), 3, "lessPopularArtist");
        addArtist(mock(Artist.class), 2, "secondPopularArtist");
    }

    private void addArtist(Artist artist, int popularity, String name) {
        when(artist.getPopularity()).thenReturn(popularity);
        when(artist.getName()).thenReturn(name);
        artistsToOrder.add(artist);
    }
}