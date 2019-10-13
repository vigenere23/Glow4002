package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import ca.ulaval.glo4002.booking.api.dtos.artists.ArtistDto;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class ApiArtistRepository implements ArtistRepository {
    private ArtistDto getArtistDto() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/artists");
        Invocation.Builder invocationBuilder
                = webTarget.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get(ArtistDto.class);
    }
}
