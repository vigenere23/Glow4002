package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistDto;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistRankingInformationMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

public class ApiArtistRepository implements ArtistRepository {
    private ArtistRankingInformationMapper artistRankingInformationMapper;

    public ApiArtistRepository(ArtistRankingInformationMapper artistRankingInformationMapper) {
        this.artistRankingInformationMapper = artistRankingInformationMapper;
    }

    @Override
    public List<ArtistRankingInformation> findArtistRankingInformation() {
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

    // TODO
}
