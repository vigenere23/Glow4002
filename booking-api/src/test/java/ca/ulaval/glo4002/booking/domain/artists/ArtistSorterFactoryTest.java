package ca.ulaval.glo4002.booking.domain.artists;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtistSorterFactoryTest {
    
    private ArtistSorterFactory artistSorterFactory;
     
    @BeforeEach
    public void setUpArtistSortingFactory() {
        artistSorterFactory = new ArtistSorterFactory();
     }
     
    @Test
    void givenLowCostsortingType_whenCreateArtistsorting_thenCreatesNewDecreasingPriceOrderedArtists() {
        ArtistSortingType artistSortingType = ArtistSortingType.LOW_COSTS;

        ArtistSortingStrategy artistsortingTest = artistSorterFactory.create(artistSortingType);

        assertTrue(artistsortingTest instanceof DecreasingPriceSorter);
    }
 
    @Test
    void givenMostPopularitysortingType_whenCreateArtistsorting_thenCreatesNewAscendingPopularityOrderedArtists() {
        ArtistSortingType sortingType = ArtistSortingType.MOST_POPULARITY;

        ArtistSortingStrategy artistsortingTest = artistSorterFactory.create(sortingType);

        assertTrue(artistsortingTest instanceof AscendingPopularitySorter);
    }
}