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

import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingStrategy;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRanking;

@ExtendWith(MockitoExtension.class)
public class ArtistRankingUseCaseTest {

    final static ArtistRanking SOME_RANKING_TYPE = ArtistRanking.LOW_COSTS;
    final static List<Artist> artistsToOrder = new ArrayList<>();
    
    @Mock ArtistRepository artistRepository;
    @Mock ArtistRankingFactory artistRankingFactory;
    @Mock ArtistRankingStrategy artistRankingStrategy;
    @InjectMocks ArtistRankingUseCase artistRankingUseCase;

    @BeforeEach
    public void setUp() {
        when(artistRepository.findAll()).thenReturn(artistsToOrder);
        when(artistRankingFactory.createArtistRanking(SOME_RANKING_TYPE)).thenReturn(artistRankingStrategy);
    }

    @Test
    public void whenOrderBy_thenFindArtistFromRepositoryIsCalled() {
        artistRankingUseCase.orderBy(SOME_RANKING_TYPE);
        verify(artistRepository).findAll();
    }

    @Test
    public void givenRankingType_whenOrderBy_thenCallMethodCreateArtistRankingFromFactory() {
        artistRankingUseCase.orderBy(SOME_RANKING_TYPE);
        verify(artistRankingFactory).createArtistRanking(SOME_RANKING_TYPE);
    }

    @Test
    public void whenOrderBy_thenGetOrderedArtists() {
        artistRankingUseCase.orderBy(SOME_RANKING_TYPE);
        verify(artistRankingStrategy).getOrderedArtists(artistsToOrder);
    }
}
