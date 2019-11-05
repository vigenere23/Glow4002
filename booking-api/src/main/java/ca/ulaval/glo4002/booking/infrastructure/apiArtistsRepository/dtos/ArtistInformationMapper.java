package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;

public class ArtistInformationMapper {
    public ArtistRankingInformation rankingFromDto(ArtistDto artistDto) {
        return new ArtistRankingInformation(artistDto.name, artistDto.popularityRank, artistDto.price);
    }

    public ArtistProgramInformation programFromDto(ArtistDto artistDto) {
        return new ArtistProgramInformation(artistDto.id, artistDto.nbPeople);
    }
}
