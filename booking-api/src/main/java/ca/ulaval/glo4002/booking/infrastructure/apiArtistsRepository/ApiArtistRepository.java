package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistDto;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistInformationMapper;

public class ApiArtistRepository implements ArtistRepository {
    private ArtistInformationMapper artistInformationMapper;

    public ApiArtistRepository(ArtistInformationMapper artistInformationMapper) {
        this.artistInformationMapper = artistInformationMapper;
    }

    @Override
    public List<ArtistRankingInformation> findArtistRankingInformation() {
        List<ArtistRankingInformation> artistRankingInformations = new ArrayList<ArtistRankingInformation>();
        List<ArtistDto> artistDtos = getArtistsDto();
        artistDtos.forEach(artistDto -> artistRankingInformations.add(artistInformationMapper.rankingFromDto(artistDto)));
        return artistRankingInformations;
    }

    private List<ArtistDto> getArtistsDto() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/artists");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get(new GenericType<List<ArtistDto>>() {
        });
    }

    @Override
    public ArtistProgramInformation getArtistByName(String artistName) {
        List<ArtistDto> artistDtos = getArtistsDto();
        return artistInformationMapper.programFromDto(artistDtos.stream()
                                        .filter(artist -> artistName.equals(artist.name))
                                        .findAny().orElse(null));
    }

    // TODO (issue #134)
}
