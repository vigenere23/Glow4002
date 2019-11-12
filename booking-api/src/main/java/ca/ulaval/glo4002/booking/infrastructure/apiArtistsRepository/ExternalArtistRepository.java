package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistDto;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistInformationMapper;

import java.util.ArrayList;
import java.util.List;

public class ExternalArtistRepository implements ArtistRepository {

    private ArtistInformationMapper artistInformationMapper;
    private ApiArtist apiArtist;

    public ExternalArtistRepository(ArtistInformationMapper artistInformationMapper,
            ApiArtist apiArtist) {
        this.artistInformationMapper = artistInformationMapper;
        this.apiArtist = apiArtist;
    }

    public List<ArtistRankingInformation> findArtistRankingInformation() {
        List<ArtistRankingInformation> artistRankingInformations = new ArrayList<>();
        List<ArtistDto> artistDtos = apiArtist.getArtistsDto();
        artistDtos
                .forEach(artistDto -> artistRankingInformations.add(artistInformationMapper.rankingFromDto(artistDto)));
        return artistRankingInformations;
    }

    @Override
    public ArtistProgramInformation getArtistByName(String artistName) {
        return artistInformationMapper.programFromDto(apiArtist.getArtistDto(artistName));
    }
}
