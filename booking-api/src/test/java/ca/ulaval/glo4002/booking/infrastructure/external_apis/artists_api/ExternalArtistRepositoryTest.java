package ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistDto;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistDtoMapper;

public class ExternalArtistRepositoryTest {

    private final static String SOME_ARTIST_NAME = "ArtistName";
    private final static int SOME_POPULARITY_RANK = 5;
    private final static Price SOME_PRICE = new Price(5000);

    private ApiArtist apiArtist;
    private ExternalApiArtistRepository externalArtistRepository;
    private List<ArtistDto> artistsDtoCollection;

    @BeforeEach
    public void setUpExternalArtistRepository() {
        apiArtist = mock(ApiArtist.class);
        ArtistDtoMapper artistInformationMapper = new ArtistDtoMapper();
        externalArtistRepository = new ExternalApiArtistRepository(artistInformationMapper, apiArtist);
        artistsDtoCollection = new ArrayList<>();
    }

    @Test
    public void givenAnyArtistFromApiArtist_whenFindArtistRankingInformation_thenNoInformationIsFound() {
        when(apiArtist.getAll()).thenReturn(new ArrayList<>());

        List<Artist> actualInformation = externalArtistRepository.findAll();

        int expectedInformationCount = 0;
        assertEquals(expectedInformationCount, actualInformation.size());
    }

    @Test
    public void givenOneArtistDto_whenFindFirstArtistRankingInformation_thenArtistRetrievedHasTheCorrectName() {
        mockOneArtistInformation();
        Artist firstArtistInformation = findFirstArtistInformation();
        assertEquals(SOME_ARTIST_NAME, firstArtistInformation.getName());
    }

    @Test
    public void givenOneArtistDto_whenFindFirstArtistRankingInformation_thenArtistRetrievedHasTheCorrectPopularityRank() {
        mockOneArtistInformation();
        Artist firstArtistInformation = findFirstArtistInformation();
        assertEquals(SOME_POPULARITY_RANK, firstArtistInformation.getPopularity());
    }

    @Test
    public void givenOneArtistDto_whenFindFirstArtistRankingInformation_thenArtistRetrievedHasTheCorrectPrice() {
        mockOneArtistInformation();
        Artist firstArtistInformation = findFirstArtistInformation();
        assertEquals(SOME_PRICE, firstArtistInformation.getPrice());
    }

    private void mockOneArtistInformation() {
        ArtistDto artistDtoToAddToMock = createOneArtistDto();
        mockArtistDtoFromApiArtist(artistDtoToAddToMock);
    }

    private Artist findFirstArtistInformation() {
        List<Artist> actualInformation = externalArtistRepository.findAll();
        return actualInformation.get(0);
    }

    private ArtistDto createOneArtistDto() {
        return new ArtistDto(1, SOME_ARTIST_NAME, 20, "MusicStyle", SOME_PRICE.getRoundedAmount(2), SOME_POPULARITY_RANK, new ArrayList<>());
    }

    private void mockArtistDtoFromApiArtist(ArtistDto artistToAddToMock) {
        artistsDtoCollection.add(artistToAddToMock);
        when(apiArtist.getAll()).thenReturn(artistsDtoCollection);
    }
}
