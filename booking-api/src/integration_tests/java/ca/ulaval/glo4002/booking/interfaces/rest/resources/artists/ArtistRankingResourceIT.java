package ca.ulaval.glo4002.booking.interfaces.rest.resources.artists;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.JerseyTestBookingServer;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.artists.responses.ArtistRankingResponse;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;

public class ArtistRankingResourceIT extends JerseyTestBookingServer {

    private static final String ARTIST_RANKING_URL = "/program/artists";
    private static final String QUERY_PARAM = "orderBy";
    private static final String MOST_POPULAR_PARAM = "mostPopular";
    private static final String LOW_COST_PARAM = "lowCosts";
    private static final int someLowArtistPopularity = 2;
    private static final int someHightArtistPopularity = 20;
    private static final float someLowArtistPrice = 50.00f;
    private static final float someHightArtistPrice = 5000.00f;

    private List<ArtistRankingInformation> artistRankingInformation;
    
    @Test
    public void givenOneArtist_whenSortByMostPopular_thenTheArtistIsReturnedWith200ResponseStatus() {
        ArtistRankingResponse expectedBody = mockOneLowPopularityArtistFromExternalRepository(); 

        Response response = getSortedArtistFromServer(MOST_POPULAR_PARAM);

        ArtistRankingResponse actualBody = response.readEntity(ArtistRankingResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedBody.artists, actualBody.artists);
    }

    @Test
    public void givenOneArtist_whenSortByLowCost_thenTheArtistIsReturnedWith200ResponseStatus() {
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
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
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
        List<ArtistRankingInformation> informationCollection = createArtistInformationCollection(notPopularArtistInformation, popularArtistInformation);
        return buildExpectedResponse(informationCollection);
    }

    private ArtistRankingResponse mockOneExpensiveAndOneCheapCostArtistFromExternalRepository() {
        ArtistRankingInformation hightCostArtist = addArtistToListInformation("Expensive artist", someLowArtistPopularity, someHightArtistPrice);
        ArtistRankingInformation lowCostArtist = addArtistToListInformation("Cheap Artist", someHightArtistPopularity, someLowArtistPrice);
        List<ArtistRankingInformation> informationCollection = createArtistInformationCollection(hightCostArtist, lowCostArtist);
        return buildExpectedResponse(informationCollection);
    }

    private ArtistRankingResponse mockTwortistWithSamePriceFromExternalRepository() {
        ArtistRankingInformation notPopularArtistInformation = addArtistToListInformation("Unknown artist", someLowArtistPopularity, someHightArtistPrice);
        ArtistRankingInformation popularArtistInformation = addArtistToListInformation("Popular Artist", someHightArtistPopularity, someHightArtistPrice);
        List<ArtistRankingInformation> informationCollection = createArtistInformationCollection(popularArtistInformation, notPopularArtistInformation);
        return buildExpectedResponse(informationCollection);
    }

    private List<ArtistRankingInformation> createArtistInformationCollection(ArtistRankingInformation firstInTheCollection, 
    ArtistRankingInformation secondInTheCollection) {
        List<ArtistRankingInformation> informationCollection = new ArrayList<ArtistRankingInformation>();
        informationCollection.add(firstInTheCollection);
        informationCollection.add(secondInTheCollection);
        return informationCollection;
    }

    private ArtistRankingResponse buildExpectedResponse(List<ArtistRankingInformation> information) {
        List<String> sortedArtist = new ArrayList<String>();
        for (ArtistRankingInformation rankingInfo : information) {
            sortedArtist.add(rankingInfo.getArtistName());
        }

        return new ArtistRankingResponse(sortedArtist);
    }

    private Response getSortedArtistFromServer(String sortingOption) {
       return target(ARTIST_RANKING_URL).queryParam(QUERY_PARAM, sortingOption).request().get();
    }

    private  ArtistRankingInformation addArtistToListInformation(String artistName,  int popularity, float price) {
        ArtistRankingInformation rankingInformation = new ArtistRankingInformation(artistName, popularity, price);
        artistRankingInformation.add(rankingInformation);
        return rankingInformation;
    }
}
