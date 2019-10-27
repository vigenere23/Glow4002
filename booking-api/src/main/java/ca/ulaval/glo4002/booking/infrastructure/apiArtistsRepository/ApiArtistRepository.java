package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import ca.ulaval.glo4002.booking.api.dtos.artists.ArtistExternalResponse;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;

import java.util.ArrayList;
import java.util.List;

public class ApiArtistRepository implements ArtistRepository {
    private ArtistExternalResponse artistExternalResponse;

    public ApiArtistRepository(ArtistExternalResponse artistExternalResponse) {
        this.artistExternalResponse = artistExternalResponse;
    }

    @Override
    public List<ArtistRankingInformation> findArtistRankingInformationById(List<Integer> idToFind) {
        List<ArtistRankingInformation> allArtistRankingInformations = artistExternalResponse.getArtistRankingInformations();
        // TODO
        // ici on doit aller chercher dnas le service externe seulement les artistes qu'on a de besoin
        //créer un ArtistRankingInformation pour chacun d'eux a partis du Dto provenant du service externe
        //renvoyer laliste les contenant tous pour que le domaine fase le sorting
        return allArtistRankingInformations;
    }
}
