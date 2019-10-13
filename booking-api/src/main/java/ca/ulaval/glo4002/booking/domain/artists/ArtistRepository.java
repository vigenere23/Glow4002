package ca.ulaval.glo4002.booking.domain.artists;

import java.util.List;

public interface ArtistRepository {

    List<ArtistRankingInformation> findArtistRankingInformationById(List<Integer> idToFind);

}
