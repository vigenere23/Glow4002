package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;

import java.util.List;

public class ApiArtistRepository implements ArtistRepository {
    private ArtistExternalResponse artistExternalResponse;

    public ApiArtistRepository(ArtistExternalResponse artistExternalResponse) {
        this.artistExternalResponse = artistExternalResponse;
    }

    @Override
    public List<ArtistRankingInformation> findArtistRankingInformation() {
        return artistExternalResponse.getArtistRankingInformations();
    }

    // TODO
}
