package ca.ulaval.glo4002.booking.infrastructure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalServiceApiArtist;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistDto;
import ca.ulaval.glo4002.organisation.OrganisationServer;

public class ExternalServiceApiArtistTest {

    private static ExternalServiceApiArtist apiArtist;
    private static Thread organizationServer;

    @BeforeAll
    public static void setUpServer() throws Exception {
        String[] arguments = new String[] { "" };
        organizationServer = new Thread(new OrganisationServer(arguments));
        organizationServer.start();
        TimeUnit.SECONDS.sleep(20);
    }

    @AfterAll
    public static void cleanUpServer() {
        organizationServer.interrupt();
    }

    @BeforeEach
    public void setUp() {
        apiArtist = new ExternalServiceApiArtist();
    }

    @AfterEach
    public void cleanupExternalArtistApi() throws IOException {
        apiArtist.close();
    }

    @Test
    @Tag("IntegrationTests")
    public void whenGetArtistDtoFromExternalService_thenSomeArtistAreReturned(){
        List<ArtistDto> artistsDto = getArtistDtoCollectionFromApiArtistServer();

        int artistDtoCount = artistsDto.size();
        assertTrue(artistDtoCount > 0, "Current answer from ArtistApi does not provide any artist.");
    }

    @Test
    @Tag("IntegrationTests")
    public void whenGetOneArtistDtoFromExternalService_thenArtistMusicStyleIsValid(){
        ArtistDto oneArtistDto = getFirstArtistDto();

        assertTrue(oneArtistDto.musicStyle != null, "First artist retrieved does not have any musicStyle.");
    }

    @Test
    @Tag("IntegrationTests")
    public void whenGetOneArtistDtoFromExternalService_thenArtistAvailabilityContainerIsNotNull(){
        ArtistDto oneArtistDto = getFirstArtistDto();

        assertTrue(oneArtistDto.availabilities != null, "First artist retrieved does not have any availability container.");
    }

    @Test
    @Tag("IntegrationTests")
    public void whenGetOneArtistDtoFromExternalService_thenArtistNameIsNotNull(){
        ArtistDto oneArtistDto = getFirstArtistDto();

        assertTrue(oneArtistDto.name != null, "First artist retrieved does not have any name.");
    }

    private static List<ArtistDto> getArtistDtoCollectionFromApiArtistServer() {
        return apiArtist.getArtistsDto();
    }

    private ArtistDto getFirstArtistDto() {
        return getArtistDtoCollectionFromApiArtistServer().get(0);
    }
}