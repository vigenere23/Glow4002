package ca.ulaval.glo4002.booking;

import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistRankingInformationMapper;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ApiArtistRepository;
import ca.ulaval.glo4002.organisation.OrganisationServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArtistFunctionalTest {

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
        ArtistRankingInformationMapper artistRankingInformationMapper = new ArtistRankingInformationMapper();
        artistRepository = new ApiArtistRepository(artistRankingInformationMapper);
    }

    @Test
    public void whenOrderSupergiantSinglePass_thenRightLocationHeader() {
        // TODO to remove/modify, for test only
        List<ArtistRankingInformation> artistRankingInformations = artistRepository.findArtistRankingInformation();
        String firstArtistName = artistRankingInformations.get(0).getArtistName();

        assertEquals("Sun 41", firstArtistName);
    }

    // TODO to complete (issue #127)
}
