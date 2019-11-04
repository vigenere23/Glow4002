package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistDto;

public class ExternalServiceApiArtist implements ApiArtist, Closeable {

    private Client client;

    public ExternalServiceApiArtist() {
        client = ClientBuilder.newClient();
    }

    public List<ArtistDto> getArtistsDto() {
        WebTarget webTarget = client.target("http://localhost:8080/artists");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get(new GenericType<List<ArtistDto>>() {
        });
    }

    @Override
    public void close() throws IOException {
        client.close();
    }
}