package ca.ulaval.glo4002.booking.application.artists;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRanking;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.artists.Ranking;

@ExtendWith(MockitoExtension.class)
public class ArtistRankingUseCaseTest {

    final static Ranking SOME_RANKING_TYPE = Ranking.LOW_COSTS;
    final static List<ArtistRankingInformation> artistsToOrder = new ArrayList<>();
    
    @Mock ArtistRepository artistRepository;
    @Mock ArtistRankingFactory artistRankingFactory;
    @Mock ArtistRanking artistRanking;
    @InjectMocks ArtistRankingUseCase artistRankingUseCase;

    @BeforeEach
    public void setUp() {
        when(artistRepository.findArtistRankingInformation()).thenReturn(artistsToOrder);
        when(artistRankingFactory.createArtistRanking(SOME_RANKING_TYPE)).thenReturn(artistRanking);
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
        verify(artistRanking).getOrderedArtists(artistsToOrder);
    }
}