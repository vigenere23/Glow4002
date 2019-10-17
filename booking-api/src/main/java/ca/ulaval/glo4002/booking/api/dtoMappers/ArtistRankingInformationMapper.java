package ca.ulaval.glo4002.booking.api.dtoMappers;

import ca.ulaval.glo4002.booking.api.dtos.artists.ArtistDto;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;

public class ArtistRankingInformationMapper {
    public ArtistRankingInformation fromDto(ArtistDto artistDto) {
        return new ArtistRankingInformation(artistDto.name, artistDto.popularityRank, artistDto.price);
    }
}
