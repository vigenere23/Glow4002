package ca.ulaval.glo4002.booking.interfaces.rest.resources.artists;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.application.artists.dtos.ArtistDtoMapper;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.JerseyTestBookingServer;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.artists.responses.ArtistSortingResponse;

public class ArtistSortingResourceIT extends JerseyTestBookingServer {

    private static final String ARTIST_sorting_URL = "/program/artists";
    private static final String QUERY_PARAM = "orderBy";
    private static final String MOST_POPULAR_PARAM = "mostPopular";
    private static final String LOW_COST_PARAM = "lowCosts";
    private static final int someLowArtistPopularity = 2;
    private static final int someHightArtistPopularity = 20;
    private static final Price someLowArtistPrice = new Price(50);
    private static final Price someHightArtistPrice = new Price(5000);
    private static final int SOME_GROUP_SIZE = 2;
    private static final PassengerNumber SOME_PASSENGER_NUMBER = new PassengerNumber(0);

    private List<Artist> artists;
    private ArtistDtoMapper artistDtoMapper;

    @BeforeEach
    public void setup() {
        artistDtoMapper = new ArtistDtoMapper();
    }
    
    @Test
    public void givenOneArtist_whenSortByMostPopular_thenTheArtistIsReturnedWith200ResponseStatus() {
        ArtistSortingResponse expectedBody = mockOneLowPopularityArtistFromExternalRepository(); 

        Response response = getSortedArtistFromServer(MOST_POPULAR_PARAM);

        ArtistSortingResponse actualBody = response.readEntity(ArtistSortingResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedBody.artists, actualBody.artists);
    }

    @Test
    public void givenOneArtist_whenSortByLowCost_thenTheArtistIsReturnedWith200ResponseStatus() {
        ArtistSortingResponse expectedBody = mockOneLowPopularityArtistFromExternalRepository(); 

        Response response = getSortedArtistFromServer(LOW_COST_PARAM);

        ArtistSortingResponse actualBody = response.readEntity(ArtistSortingResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedBody.artists, actualBody.artists);
    }

    @Test
    public void givenTwoArtist_whenSortByMostPopular_thenFirstArtistReturnIsTheMostPopularWith200ResponseStatus() {
        ArtistSortingResponse expectedBody = mockOneHightAndOneLowPopularityArtistFromExternalRepository();

        Response response = getSortedArtistFromServer(MOST_POPULAR_PARAM);

        ArtistSortingResponse actualBody = response.readEntity(ArtistSortingResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedBody.artists, actualBody.artists);
    }

    @Test
    public void givenTwoArtist_whenSortByLowCost_thenTheFirstArtistReturnedIsTheMostExpensiveArtistWith200ResponseStatus() {
        ArtistSortingResponse expectedBody = mockOneExpensiveAndOneCheapCostArtistFromExternalRepository();

        Response response = getSortedArtistFromServer(LOW_COST_PARAM);

        ArtistSortingResponse actualBody = response.readEntity(ArtistSortingResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedBody.artists, actualBody.artists);
    }

    @Test
    public void givenTwoArtistWithSamePrice_whenSortByLowCost_thenTheFirstArtistReturnedIsTheMostPopularArtistWith200ResponseStatus() {
        ArtistSortingResponse expectedBody = mockTwortistWithSamePriceFromExternalRepository();

        Response response = getSortedArtistFromServer(LOW_COST_PARAM);

        ArtistSortingResponse actualBody = response.readEntity(ArtistSortingResponse.class);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedBody.artists, actualBody.artists);
    }

    @Test
    public void givenOneArtist_whenSortWithInvalidParameter_thenAResponseIsReturnedWithWithBadRequestResponseStatus() {
        Response response = getSortedArtistFromServer("");
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    private ArtistSortingResponse mockOneLowPopularityArtistFromExternalRepository() {
        Artist information = addArtistToListInformation("notPopularArtistName", someLowArtistPopularity, someLowArtistPrice);
        List<Artist> informationCollection = new ArrayList<Artist>();
        informationCollection.add(information);
        return buildExpectedResponse(informationCollection);
    }

    private ArtistSortingResponse mockOneHightAndOneLowPopularityArtistFromExternalRepository() {
        Artist popularArtistInformation = addArtistToListInformation("Popular Artist", someHightArtistPopularity, someLowArtistPrice);
        Artist notPopularArtistInformation = addArtistToListInformation("Unknown Artist", someLowArtistPopularity, someLowArtistPrice);
        List<Artist> informationCollection = createArtistInformationCollection(notPopularArtistInformation, popularArtistInformation);
        return buildExpectedResponse(informationCollection);
    }

    private ArtistSortingResponse mockOneExpensiveAndOneCheapCostArtistFromExternalRepository() {
        Artist hightCostArtist = addArtistToListInformation("Expensive artist", someLowArtistPopularity, someHightArtistPrice);
        Artist lowCostArtist = addArtistToListInformation("Cheap Artist", someHightArtistPopularity, someLowArtistPrice);
        List<Artist> informationCollection = createArtistInformationCollection(hightCostArtist, lowCostArtist);
        return buildExpectedResponse(informationCollection);
    }

    private ArtistSortingResponse mockTwortistWithSamePriceFromExternalRepository() {
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

    private ArtistSortingResponse buildExpectedResponse(List<Artist> artists) {
        return new ArtistSortingResponse(artistDtoMapper.toDtos(artists));
    }

    private Response getSortedArtistFromServer(String sortingOption) {
       return target(ARTIST_sorting_URL).queryParam(QUERY_PARAM, sortingOption).request().get();
    }

    private  Artist addArtistToListInformation(String artistName,  int popularity, Price price) {
        Artist sortingInformation = new Artist(artistName, popularity, price, SOME_GROUP_SIZE, SOME_PASSENGER_NUMBER);
        artists.add(sortingInformation);
        return sortingInformation;
    }
}
