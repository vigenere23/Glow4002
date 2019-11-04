package ca.ulaval.glo4002.booking.domain.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.willReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRanking;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.artists.DecreasingPriceOrderedArtists;
import ca.ulaval.glo4002.booking.domain.artists.Ranking;

public class ArtistRankingUseCaseTest {

    private final static Ranking SOME_RANKING_TYPE = Ranking.LOW_COSTS;
    private final static List<ArtistRankingInformation> artistsToOrder = new ArrayList<>();
    
    private ArtistRepository artistRepository;
    private ArtistRankingFactory artistRankingFactory;
    private ArtistRankingUseCase artistRankingUseCase;
    private ArtistRanking decreasingPriceOrderedArtists;

    @BeforeEach
    public void setUp() {
        artistRepository = mock(ArtistRepository.class);
        artistRankingFactory = mock(ArtistRankingFactory.class);
        decreasingPriceOrderedArtists = mock(DecreasingPriceOrderedArtists.class);
        artistRankingUseCase = new ArtistRankingUseCase(artistRepository, artistRankingFactory);
    
        willReturn(artistsToOrder).given(artistRepository).findArtistRankingInformation();
        willReturn(decreasingPriceOrderedArtists).given(artistRankingFactory).createArtistRanking(SOME_RANKING_TYPE);
    }

    @Test
    public void whenOrderBy_thenFindArtistRankingInformationFromRepositoryIsCalled() {
        artistRankingUseCase.orderBy(SOME_RANKING_TYPE);
        verify(artistRepository).findArtistRankingInformation();
    }

    @Test
    public void givenRankingType_whenOrderBy_thenCallMethodCreateArtistRankingFromFactory() {
        artistRankingUseCase.orderBy(SOME_RANKING_TYPE);
        verify(artistRankingFactory).createArtistRanking(SOME_RANKING_TYPE);
    }

    @Test
    public void whenOrderBy_thenGetOrderedArtists() {
        artistRankingUseCase.orderBy(SOME_RANKING_TYPE);
        verify(decreasingPriceOrderedArtists).getOrderedArtists(artistsToOrder);
    }
}