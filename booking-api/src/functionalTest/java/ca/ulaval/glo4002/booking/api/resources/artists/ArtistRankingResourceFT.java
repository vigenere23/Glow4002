package ca.ulaval.glo4002.booking.api.resources.artists;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;

public class ArtistRankingResourceFT extends JerseyTest {

    private static final String ARTIST_RANKING_URL = "/program/artists";
    private static final String QUERY_PARAM = "orderBy";
    private static final String MOST_POPULAR_PARAM = "mostPopular";
    private static final String LOW_COST_PARAM = "lowCosts";
    private static final int someLowArtistPopularity = 2;
    private static final int someHightArtistPopularity = 20;
    private static final float someLowArtistPrice = 50.00f;
    private static final float someHightArtistPrice = 5000.00f;

    private ArtistRepository   artistsRepositoryMock = mock(ArtistRepository.class);
    private ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
    private ArtistRankingUseCase artistRankingUseCase = new ArtistRankingUseCase(artistsRepositoryMock, artistRankingFactory);  
    private List<ArtistRankingInformation> artistRankingInformation;
    
    @BeforeEach
    @Override
    public void setUp() throws Exception {
        createResources();
        when(artistsRepositoryMock.findArtistRankingInformation()).thenReturn(artistRankingInformation);   
        super.setUp();
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected Application configure() {     
        ResourceConfig resourceConfig = new ResourceConfig(ArtistsRankingResource.class);
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(artistRankingUseCase).to(ArtistRankingUseCase.class);
            }
        });
        return resourceConfig;
    }

    @Test
    public void givenOneArtist_whenSortByMostPopular_thenTheOneArtistIsReturnedWith200ResponseStatus() {
        ArtistRankingResponse expectedBody = mockOneLowPopularityArtistFromExternalRepository(); 

        Response response = getSortedArtistFromServer(MOST_POPULAR_PARAM);

        ArtistRankingResponse actualBody = response.readEntity(ArtistRankingResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedBody.artists, actualBody.artists);
    }

    @Test
    public void givenOneArtist_whenSortByLowCost_thenTheOneArtistIsReturnedWith200ResponseStatus() {
        ArtistRankingResponse expectedBody = mockOneLowPopularityArtistFromExternalRepository(); 

        Response response = getSortedArtistFromServer(LOW_COST_PARAM);

        ArtistRankingResponse actualBody = response.readEntity(ArtistRankingResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedBody.artists, actualBody.artists);
    }

    @Test
    public void givenTwoArtist_whenSortByMostPopular_thenFirstArtistReturnIsTheMostPopularWith200ResponseStatus() {
        ArtistRankingResponse expectedBody = mockOneHightAndOneLowPopularityArtistFromExternalRepository();

        Response response = getSortedArtistFromServer(MOST_POPULAR_PARAM);

        ArtistRankingResponse actualBody = response.readEntity(ArtistRankingResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedBody.artists, actualBody.artists);
    }

    @Test
    public void givenTwoArtist_whenSortByLowCost_thenTheFirstArtistReturnedIsTheMostExpensiveArtistWith200ResponseStatus() {
        ArtistRankingResponse expectedBody = mockOneExpensiveAndOneCheapCostArtistFromExternalRepository();

        Response response = getSortedArtistFromServer(LOW_COST_PARAM);

        ArtistRankingResponse actualBody = response.readEntity(ArtistRankingResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedBody.artists, actualBody.artists);
    }

    @Test
    public void givenTwoArtistWithSamePrice_whenSortByLowCost_thenTheFirstArtistReturnedIsTheMostPopularArtistWith200ResponseStatus() {
        ArtistRankingResponse expectedBody = mockTwortistWithSamePriceFromExternalRepository();

        Response response = getSortedArtistFromServer(LOW_COST_PARAM);

        ArtistRankingResponse actualBody = response.readEntity(ArtistRankingResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedBody.artists, actualBody.artists);
    }

    @Test
    public void givenOneArtist_whenSortWithInvalidParameter_thenAResponseIsReturnedWithWithBadRequestResponseStatus() {
        Response response = getSortedArtistFromServer("");
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    private ArtistRankingResponse mockOneLowPopularityArtistFromExternalRepository() {
        ArtistRankingInformation information = addArtistToListInformation("notPopularArtistName", someLowArtistPopularity, someLowArtistPrice);
        List<ArtistRankingInformation> informationCollection = new ArrayList<ArtistRankingInformation>();
        informationCollection.add(information);
        return buildExpectedResponse(informationCollection);
    }

    private ArtistRankingResponse mockOneHightAndOneLowPopularityArtistFromExternalRepository() {
        ArtistRankingInformation popularArtistInformation = addArtistToListInformation("Popular Artist", someHightArtistPopularity, someLowArtistPrice);
        ArtistRankingInformation notPopularArtistInformation = addArtistToListInformation("Unknown Artist", someLowArtistPopularity, someLowArtistPrice);
        List<ArtistRankingInformation> informationCollection = new ArrayList<ArtistRankingInformation>();
        informationCollection.add(notPopularArtistInformation);
        informationCollection.add(popularArtistInformation);
        return buildExpectedResponse(informationCollection);
    }

    private ArtistRankingResponse mockOneExpensiveAndOneCheapCostArtistFromExternalRepository() {
        ArtistRankingInformation hightCostArtist = addArtistToListInformation("Expensive artist", someLowArtistPopularity, someHightArtistPrice);
        ArtistRankingInformation lowCostArtist = addArtistToListInformation("Cheap Artist", someHightArtistPopularity, someLowArtistPrice);
        List<ArtistRankingInformation> informationCollection = new ArrayList<ArtistRankingInformation>();
        informationCollection.add(hightCostArtist);
        informationCollection.add(lowCostArtist);
        return buildExpectedResponse(informationCollection);
    }

    private ArtistRankingResponse mockTwortistWithSamePriceFromExternalRepository() {
        ArtistRankingInformation notPopularArtistInformation = addArtistToListInformation("Unknown artist", someLowArtistPopularity, someHightArtistPrice);
        ArtistRankingInformation popularArtistInformation = addArtistToListInformation("Popular Artist", someHightArtistPopularity, someHightArtistPrice);
        List<ArtistRankingInformation> informationCollection = new ArrayList<ArtistRankingInformation>();
        informationCollection.add(popularArtistInformation);
        informationCollection.add(notPopularArtistInformation);
        return buildExpectedResponse(informationCollection);
    }

    private ArtistRankingResponse buildExpectedResponse(List<ArtistRankingInformation> information) {
        List<String> sortedArtist = new ArrayList<String>();
        for (ArtistRankingInformation rankingInfo : information) {
            sortedArtist.add(rankingInfo.getArtistName());
        }

        ArtistRankingResponse response = new ArtistRankingResponse();
        response.artists = sortedArtist;
        return response;
    }

    private Response getSortedArtistFromServer(String sortingOption) {
       return target(ARTIST_RANKING_URL).queryParam(QUERY_PARAM, sortingOption).request().get();
    }

    private  ArtistRankingInformation addArtistToListInformation(String artistName,  int popularity, float price) {
        ArtistRankingInformation rankingInformation = new ArtistRankingInformation(artistName, popularity, price);
        artistRankingInformation.add(rankingInformation);
        return rankingInformation;
    }

    private void createResources() {  
        artistRankingInformation = new ArrayList<>();    
    }
}