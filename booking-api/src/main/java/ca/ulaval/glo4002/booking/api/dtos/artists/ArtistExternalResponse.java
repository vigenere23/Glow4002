package ca.ulaval.glo4002.booking.api.dtos.artists;

import ca.ulaval.glo4002.booking.api.dtoMappers.ArtistRankingInformationMapper;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

public class ArtistExternalResponse {
    private ArtistRankingInformationMapper artistRankingInformationMapper;

    public ArtistExternalResponse(ArtistRankingInformationMapper artistRankingInformationMapper) {
        this.artistRankingInformationMapper = artistRankingInformationMapper;
    }

    public List<ArtistRankingInformation> getArtistRankingInformations() {
        List<ArtistRankingInformation> artistRankingInformations = new ArrayList<ArtistRankingInformation>();
        List<ArtistDto> artistDtos = getArtistsDto();
        artistDtos.forEach(artistDto -> artistRankingInformations.add(artistRankingInformationMapper.fromDto(artistDto)));
        return artistRankingInformations;
    }

    private List<ArtistDto> getArtistsDto() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/artists");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get(new GenericType<List<ArtistDto>>() {});
    }
}
