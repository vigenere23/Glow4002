package ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistDto;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistInformationMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ExternalApiArtistRepository implements ArtistRepository {

    private ArtistInformationMapper artistInformationMapper;
    private ApiArtist apiArtist;

    @Inject
    public ExternalApiArtistRepository(ArtistInformationMapper artistInformationMapper, ApiArtist apiArtist) {
        this.artistInformationMapper = artistInformationMapper;
        this.apiArtist = apiArtist;
    }

    public List<ArtistRankingInformation> findArtistRankingInformation() {
        List<ArtistRankingInformation> artistRankingInformations = new ArrayList<>();
        List<ArtistDto> artistDtos = apiArtist.getArtistsDto();
        artistDtos.forEach(artistDto -> artistRankingInformations
                    .add(artistInformationMapper.rankingFromDto(artistDto)));
        return artistRankingInformations;
    }

    @Override
    public List<ArtistProgramInformation> getArtistsForProgram() {
        List<ArtistProgramInformation> artistProgramInformations = new ArrayList<>();
        List<ArtistDto> artistDtos = apiArtist.getArtistsDto();
        artistDtos.forEach(artistDto -> artistProgramInformations
                    .add(artistInformationMapper.programFromDto(artistDto)));
        return artistProgramInformations;  
    }
}
