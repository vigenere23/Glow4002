package ca.ulaval.glo4002.booking.domain.artists;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtistRankingFactoryTest {
    
    private ArtistRankingFactory artistRankingFactory;
     
    @BeforeEach
    public void setUpArtistRankingFactory() {
        artistRankingFactory = new ArtistRankingFactory();
     }
     
    @Test
    void givenLowCostRankingType_whenCreateArtistRanking_thenCreatesNewDecreasingPriceOrderedArtists() {
        ArtistRanking rankingType = ArtistRanking.LOW_COSTS;

        ArtistRankingStrategy artistRankingTest = artistRankingFactory.createArtistRanking(rankingType);

        assertTrue(artistRankingTest instanceof DecreasingPriceOrderedArtists);
    }
 
    @Test
    void givenMostPopularityRankingType_whenCreateArtistRanking_thenCreatesNewAscendingPopularityOrderedArtists() {
        ArtistRanking rankingType = ArtistRanking.MOST_POPULARITY;

        ArtistRankingStrategy artistRankingTest = artistRankingFactory.createArtistRanking(rankingType);

        assertTrue(artistRankingTest instanceof AscendingPopularityOrderedArtists);
    }
}