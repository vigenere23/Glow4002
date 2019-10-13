package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import ca.ulaval.glo4002.booking.api.dtos.artists.ArtistDto;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class ApiArtistRepository implements ArtistRepository {

    private ArtistDto getArtistsDto() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/artists");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get(ArtistDto.class);
    }

    @Override
    public List<ArtistRankingInformation> findArtistRankingInformationById(List<Integer> idToFind) {
        // ici on doit aller chercher dnas le service externe seulement les artistes qu'on a de besoin 
        //créer un ArtistRankingInformation pour chacun d'eux a partis du Dto provenant du service externe
        //renvoyer laliste les contenant tous pour que le domaine fase le sorting
        return null;
    }
}
