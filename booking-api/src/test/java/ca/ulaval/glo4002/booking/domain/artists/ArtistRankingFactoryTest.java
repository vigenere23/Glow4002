package ca.ulaval.glo4002.booking.domain.artists;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtistRankingFactoryTest {
    
    private ArtistRankingFactory artistRankingFactory;
     
     @BeforeEach
     public void setUp() {
         artistRankingFactory = new ArtistRankingFactory();
     }
     
     @Test
     void givenLowCostRankingType_whenCreateArtistRanking_thenCreatesNewDecreasingPriceOrderedArtists() {
         ArtistRanking artistRankingTest = artistRankingFactory.createArtistRanking(Ranking.LOW_COSTS);
         assertTrue(artistRankingTest instanceof DecreasingPriceOrderedArtists);
     }
 
     @Test
     void givenMostPopularityRankingType_whenCreateArtistRanking_thenCreatesNewAscendingPopularityOrderedArtists() {
        ArtistRanking artistRankingTest = artistRankingFactory.createArtistRanking(Ranking.MOST_POPULARITY);
        assertTrue(artistRankingTest instanceof AscendingPopularityOrderedArtists);
     }
 }