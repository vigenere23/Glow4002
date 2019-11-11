package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistDto;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistRankingInformationMapper;

import java.util.ArrayList;
import java.util.List;

public class ExternalArtistRepository implements ArtistRepository {
    
    private ArtistRankingInformationMapper artistRankingInformationMapper;
    private ApiArtist apiArtist;

    public ExternalArtistRepository(ArtistRankingInformationMapper artistRankingInformationMapper, ApiArtist apiArtist) {
        this.artistRankingInformationMapper = artistRankingInformationMapper;
        this.apiArtist = apiArtist;
    }

    public List<ArtistRankingInformation> findArtistRankingInformation() {
        List<ArtistRankingInformation> artistRankingInformations = new ArrayList<>();
        List<ArtistDto> artistDtos = apiArtist.getArtistsDto();
        artistDtos.forEach(artistDto -> artistRankingInformations.add(artistRankingInformationMapper.fromDto(artistDto)));
        return artistRankingInformations;
    }
}
