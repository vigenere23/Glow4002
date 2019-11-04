package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistDto;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistRankingInformationMapper;

public class ApiArtistRepositoryTest {

    private final String ARTIST_NAME = "ArtistName";
    private final int POPULARITY_RANK = 5;
    private final float PRICE = 5000.00f;

    private ApiArtist apiArtist;
    private ApiArtistRepository apiArtistRepository;
    private ArtistRankingInformationMapper artistRankingInformationMapper;
    private List<ArtistDto> artistsDtoCollection;

    @BeforeEach
    public void setUpApiArtistRepositoryTest() {
        apiArtist = mock(ApiArtist.class);
        artistRankingInformationMapper = new ArtistRankingInformationMapper();
        apiArtistRepository = new ApiArtistRepository(artistRankingInformationMapper, apiArtist);
        artistsDtoCollection = new ArrayList<ArtistDto>();
    }

    @Test
    public void givenAnyArtistFromApiArtist_whenFindArtistRankingInformation_thenNoInformationIsFound() {
        when(apiArtist.getArtistsDto()).thenReturn(new ArrayList<ArtistDto>());  
          
        List<ArtistRankingInformation> actualInformation = apiArtistRepository.findArtistRankingInformation();
        
        int expectedInformationCount = 0;
        int actualInformationCount = actualInformation.size();
        assertEquals(expectedInformationCount, actualInformationCount);
    }

    @Test
    public void givenOneArtistDto_whenFindFirstArtistRankingInformation_thenArtistRetrievedHasTheCorrectName() {
      mockOneArtistInformation();

      ArtistRankingInformation firstArtistInformation = findFirstArtistInformation();

      String expectedName = ARTIST_NAME;
      String actualName = firstArtistInformation.getArtistName();
      assertEquals(expectedName, actualName);
    }

    @Test
    public void givenOneArtistDto_whenFindFirstArtistRankingInformation_thenArtistRetrievedHasTheCorrectPopularityRank() {
      mockOneArtistInformation();

      ArtistRankingInformation firstArtistInformation = findFirstArtistInformation();

      int expectedPopularityRank = POPULARITY_RANK;
      int actualPopularityRank = firstArtistInformation.getPopularity();
      assertEquals(expectedPopularityRank, actualPopularityRank);
    }

    @Test
    public void givenOneArtistDto_whenFindFirstArtistRankingInformation_thenArtistRetrievedHasTheCorrectPrice() {
      mockOneArtistInformation();

      ArtistRankingInformation firstArtistInformation = findFirstArtistInformation();

      float expectedPrice = PRICE;
      float actualPrice = firstArtistInformation.getPrice();
      assertEquals(expectedPrice, actualPrice);
    }

    private void mockOneArtistInformation() {
        ArtistDto artistDtoToAddToMock = createOneArtistDto();
          mockArtistDtoFromApiArtist(artistDtoToAddToMock);
    }

    private ArtistRankingInformation findFirstArtistInformation() {
        List<ArtistRankingInformation> actualInformation = apiArtistRepository.findArtistRankingInformation();
          ArtistRankingInformation firstArtistInformation= actualInformation.get(0);
        return firstArtistInformation;
    }

    private ArtistDto createOneArtistDto() {
        return new ArtistDto(1, ARTIST_NAME, 20, "MusicStyle", PRICE, POPULARITY_RANK, new ArrayList());
    }

    private void mockArtistDtoFromApiArtist(ArtistDto artistToAddToMock) {       
        artistsDtoCollection.add(artistToAddToMock);
        when(apiArtist.getArtistsDto()).thenReturn(artistsDtoCollection); 
    }
}
