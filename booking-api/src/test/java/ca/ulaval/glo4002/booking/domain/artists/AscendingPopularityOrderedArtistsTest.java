package ca.ulaval.glo4002.booking.domain.artists;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;

public class AscendingPopularityOrderedArtistsTest {

    private List<ArtistRankingInformation> artistsToOrder = new ArrayList<>();
    private AscendingPopularityOrderedArtists ascendingPopularityOrderedArtists = new AscendingPopularityOrderedArtists();

    @Test
    public void givenArtistsToOrder_whenGetOrderedArtists_thenOrderArtistsNameFromMostPopularToLessPopular() {
        fillArtistsToOrder();

        List<String> orderedArtists = ascendingPopularityOrderedArtists.getOrderedArtists(artistsToOrder);

        assertEquals("mostPopularArtist", orderedArtists.get(0));
        assertEquals("lessPopularArtist", orderedArtists.get(2));
    }

    private void fillArtistsToOrder() {
        addArtist(mock(ArtistRankingInformation.class), 1, "mostPopularArtist");
        addArtist(mock(ArtistRankingInformation.class), 3, "lessPopularArtist");
        addArtist(mock(ArtistRankingInformation.class), 2, "secondPopularArtist");
    }

    private void addArtist(ArtistRankingInformation artist, int popularity, String name) {
        willReturn(popularity).given(artist).getPopularity();
        willReturn(name).given(artist).getArtistName();
        artistsToOrder.add(artist);
    }
}