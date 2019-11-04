package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistDto;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistRankingInformationMapper;

import java.util.ArrayList;
import java.util.List;

public class ApiArtistRepository implements ArtistRepository {
    
    private ArtistRankingInformationMapper artistRankingInformationMapper;
    private ApiArtist apiArtist;

    public ApiArtistRepository(ArtistRankingInformationMapper artistRankingInformationMapper, ApiArtist apiArtist) {
        this.artistRankingInformationMapper = artistRankingInformationMapper;
        this.apiArtist = apiArtist;
    }

    public List<ArtistRankingInformation> findArtistRankingInformation() {
        List<ArtistRankingInformation> artistRankingInformations = new ArrayList<ArtistRankingInformation>();
        List<ArtistDto> artistDtos = apiArtist.getArtistsDto();
        artistDtos.forEach(artistDto -> artistRankingInformations.add(artistRankingInformationMapper.fromDto(artistDto)));
        return artistRankingInformations;
    }
}
