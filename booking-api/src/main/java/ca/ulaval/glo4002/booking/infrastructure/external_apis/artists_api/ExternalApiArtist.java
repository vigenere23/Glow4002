package ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistDto;

public class ExternalApiArtist implements ApiArtist {

    private final String ARTIST_REPOSITORY_URL = "http://localhost:8080/artists";

    public List<ArtistDto> getArtistsDto() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(ARTIST_REPOSITORY_URL);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        List<ArtistDto> artistDtos = invocationBuilder.get(new GenericType<List<ArtistDto>>() {});
        client.close();
        return artistDtos;
    }
}
