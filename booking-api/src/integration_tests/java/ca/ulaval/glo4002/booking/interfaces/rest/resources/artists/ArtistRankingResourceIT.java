package ca.ulaval.glo4002.booking.interfaces.rest.resources.artists;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.JerseyTestBookingServer;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.artists.responses.ArtistRankingResponse;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;

public class ArtistRankingResourceIT extends JerseyTestBookingServer {

    private static final String ARTIST_RANKING_URL = "/program/artists";
    private static final String QUERY_PARAM = "orderBy";
    private static final String MOST_POPULAR_PARAM = "mostPopular";
    private static final String LOW_COST_PARAM = "lowCosts";
    private static final int someLowArtistPopularity = 2;
    private static final int someHightArtistPopularity = 20;
    private static final Price someLowArtistPrice = new Price(50);
    private static final Price someHightArtistPrice = new Price(5000);
    private static final int SOME_GROUP_SIZE = 2;
    private static final PassengerNumber SOME_PASSENGER_NUMBER = new PassengerNumber(0);

    private List<Artist> Artist;
    
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
        Artist information = addArtistToListInformation("notPopularArtistName", someLowArtistPopularity, someLowArtistPrice);
        List<Artist> informationCollection = new ArrayList<Artist>();
        informationCollection.add(information);
        return buildExpectedResponse(informationCollection);
    }

    private ArtistRankingResponse mockOneHightAndOneLowPopularityArtistFromExternalRepository() {
        Artist popularArtistInformation = addArtistToListInformation("Popular Artist", someHightArtistPopularity, someLowArtistPrice);
        Artist notPopularArtistInformation = addArtistToListInformation("Unknown Artist", someLowArtistPopularity, someLowArtistPrice);
        List<Artist> informationCollection = createArtistInformationCollection(notPopularArtistInformation, popularArtistInformation);
        return buildExpectedResponse(informationCollection);
    }

    private ArtistRankingResponse mockOneExpensiveAndOneCheapCostArtistFromExternalRepository() {
        Artist hightCostArtist = addArtistToListInformation("Expensive artist", someLowArtistPopularity, someHightArtistPrice);
        Artist lowCostArtist = addArtistToListInformation("Cheap Artist", someHightArtistPopularity, someLowArtistPrice);
        List<Artist> informationCollection = createArtistInformationCollection(hightCostArtist, lowCostArtist);
        return buildExpectedResponse(informationCollection);
    }

    private ArtistRankingResponse mockTwortistWithSamePriceFromExternalRepository() {
        Artist notPopularArtistInformation = addArtistToListInformation("Unknown artist", someLowArtistPopularity, someHightArtistPrice);
        Artist popularArtistInformation = addArtistToListInformation("Popular Artist", someHightArtistPopularity, someHightArtistPrice);
        List<Artist> informationCollection = createArtistInformationCollection(popularArtistInformation, notPopularArtistInformation);
        return buildExpectedResponse(informationCollection);
    }

    private List<Artist> createArtistInformationCollection(Artist firstInTheCollection, 
    Artist secondInTheCollection) {
        List<Artist> informationCollection = new ArrayList<Artist>();
        informationCollection.add(firstInTheCollection);
        informationCollection.add(secondInTheCollection);
        return informationCollection;
    }

    private ArtistRankingResponse buildExpectedResponse(List<Artist> information) {
        List<String> sortedArtist = new ArrayList<String>();
        for (Artist rankingInfo : information) {
            sortedArtist.add(rankingInfo.getName());
        }

        return new ArtistRankingResponse(sortedArtist);
    }

    private Response getSortedArtistFromServer(String sortingOption) {
       return target(ARTIST_RANKING_URL).queryParam(QUERY_PARAM, sortingOption).request().get();
    }

    private  Artist addArtistToListInformation(String artistName,  int popularity, Price price) {
        Artist rankingInformation = new Artist(artistName, popularity, price, SOME_GROUP_SIZE, SOME_PASSENGER_NUMBER);
        Artist.add(rankingInformation);
        return rankingInformation;
    }
}
