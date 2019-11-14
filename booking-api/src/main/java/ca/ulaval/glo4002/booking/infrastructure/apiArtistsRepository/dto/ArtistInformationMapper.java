package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;

public class ArtistInformationMapper {
    public ArtistRankingInformation rankingFromDto(ArtistDto artistDto) {
        return new ArtistRankingInformation(artistDto.name, artistDto.popularityRank, artistDto.price);
    }

    public ArtistProgramInformation programFromDto(ArtistDto artistDto) {
        return new ArtistProgramInformation(artistDto.name, new PassengerNumber(artistDto.id), artistDto.nbPeople, new Price(artistDto.price));
    }
}