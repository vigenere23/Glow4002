package ca.ulaval.glo4002.booking.integration;

import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistRankingInformationMapper;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ArtistExternalResponse;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ApiArtistRepository;
import ca.ulaval.glo4002.organisation.OrganisationServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArtistFunctionalTest {
    private static final int SOME_ID = 4;
    private List<Integer> someIdToFind = new ArrayList<>();

    private ArtistRepository artistRepository;

    @BeforeAll
    public static void setUpServer() throws Exception {
        String[] arguments = new String[]{""};
        Thread organizationServer = new Thread(new OrganisationServer(arguments));
        organizationServer.start();
        TimeUnit.SECONDS.sleep(20);
    }

    @BeforeEach
    public void setUp() {
        initializeIdToFind();
        ArtistRankingInformationMapper artistRankingInformationMapper = new ArtistRankingInformationMapper();
        ArtistExternalResponse artistExternalResponse = new ArtistExternalResponse(artistRankingInformationMapper);
        artistRepository = new ApiArtistRepository(artistExternalResponse);
    }

    @Test
    public void whenOrderSupergiantSinglePass_thenRightLocationHeader() {
        // TODO to remote, for test only
        List<ArtistRankingInformation> artistRankingInformations = artistRepository.findArtistRankingInformationById(someIdToFind);
        String firstArtistName = artistRankingInformations.get(0).getArtistName();

        assertEquals("Sun 41", firstArtistName);
    }

    private void initializeIdToFind() {
        someIdToFind.add(SOME_ID);
    }
}
