package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistDto;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistInformationMapper;

public class ExternalArtistRepositoryTest {

    private final static String SOME_ARTIST_NAME = "ArtistName";
    private final static int SOME_POPULARITY_RANK = 5;
    private final static float SOME_PRICE = 5000.00f;

    private ApiArtist apiArtist;
    private ExternalArtistRepository externalArtistRepository;
    private List<ArtistDto> artistsDtoCollection;

    @BeforeEach
    public void setUpExternalArtistRepository() {
        apiArtist = mock(ApiArtist.class);
        ArtistInformationMapper artistInformationMapper = new ArtistInformationMapper();
        externalArtistRepository = new ExternalArtistRepository(artistInformationMapper, apiArtist);
        artistsDtoCollection = new ArrayList<>();
    }

    @Test
    public void givenAnyArtistFromApiArtist_whenFindArtistRankingInformation_thenNoInformationIsFound() {
        when(apiArtist.getArtistsDto()).thenReturn(new ArrayList<>());

        List<ArtistRankingInformation> actualInformation = externalArtistRepository.findArtistRankingInformation();

        int expectedInformationCount = 0;
        assertEquals(expectedInformationCount, actualInformation.size());
    }

    @Test
    public void givenOneArtistDto_whenFindFirstArtistRankingInformation_thenArtistRetrievedHasTheCorrectName() {
        mockOneArtistInformation();
        ArtistRankingInformation firstArtistInformation = findFirstArtistInformation();
        assertEquals(SOME_ARTIST_NAME, firstArtistInformation.getArtistName());
    }

    @Test
    public void givenOneArtistDto_whenFindFirstArtistRankingInformation_thenArtistRetrievedHasTheCorrectPopularityRank() {
        mockOneArtistInformation();
        ArtistRankingInformation firstArtistInformation = findFirstArtistInformation();
        assertEquals(SOME_POPULARITY_RANK, firstArtistInformation.getPopularity());
    }

    @Test
    public void givenOneArtistDto_whenFindFirstArtistRankingInformation_thenArtistRetrievedHasTheCorrectPrice() {
        mockOneArtistInformation();
        ArtistRankingInformation firstArtistInformation = findFirstArtistInformation();
        assertEquals(SOME_PRICE, firstArtistInformation.getPrice());
    }

    private void mockOneArtistInformation() {
        ArtistDto artistDtoToAddToMock = createOneArtistDto();
        mockArtistDtoFromApiArtist(artistDtoToAddToMock);
    }

    private ArtistRankingInformation findFirstArtistInformation() {
        List<ArtistRankingInformation> actualInformation = externalArtistRepository.findArtistRankingInformation();
        return actualInformation.get(0);
    }

    private ArtistDto createOneArtistDto() {
        return new ArtistDto(1, SOME_ARTIST_NAME, 20, "MusicStyle", SOME_PRICE, SOME_POPULARITY_RANK, new ArrayList<>());
    }

    private void mockArtistDtoFromApiArtist(ArtistDto artistToAddToMock) {
        artistsDtoCollection.add(artistToAddToMock);
        when(apiArtist.getArtistsDto()).thenReturn(artistsDtoCollection);
    }
}
