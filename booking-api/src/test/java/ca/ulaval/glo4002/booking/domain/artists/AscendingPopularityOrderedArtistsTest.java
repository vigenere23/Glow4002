package ca.ulaval.glo4002.booking.domain.artists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AscendingPopularityOrderedArtistsTest {

    private List<Artist> artistsToOrder = new ArrayList<>();
    private AscendingPopularitySorter ascendingPopularitySorter = new AscendingPopularitySorter();

    @Test
    public void givenArtistsToOrder_whenGetOrderedArtists_thenOrderArtistsNameFromMostPopularToLessPopular() {
        fillArtistsToOrder();

        List<Artist> orderedArtists = ascendingPopularitySorter.getSortedArtists(artistsToOrder);

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