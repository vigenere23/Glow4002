package ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistDto;
import ca.ulaval.glo4002.organisation.OrganisationServer;

public class ExternalApiArtistFT {

    private static ExternalApiArtist apiArtist;
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
        apiArtist = new ExternalApiArtist();
    }

    @Test
    public void whenGetArtistDtoFromExternalService_thenSomeArtistsAreReturned(){
        List<ArtistDto> artistsDto = getArtistDtoCollectionFromApiArtistServer();

        int artistDtoCount = artistsDto.size();
        assertTrue(artistDtoCount > 0, "Current answer from ArtistApi does not provide any artist.");
    }

    @Test
    public void whenGetOneArtistDtoFromExternalService_thenArtistMusicStyleIsValid(){
        ArtistDto oneArtistDto = getFirstArtistDto();

        assertFalse(oneArtistDto.musicStyle.isEmpty(), "First artist retrieved does not have any musicStyle.");
    }

    @Test
    public void whenGetOneArtistDtoFromExternalService_thenArtistAvailabilityContainerIsValid(){
        ArtistDto oneArtistDto = getFirstArtistDto();

        assertFalse(oneArtistDto.availabilities.equals(null), "First artist retrieved does not have any availability container.");
    }

    @Test
    public void whenGetOneArtistDtoFromExternalService_thenArtistNameIsValid(){
        ArtistDto oneArtistDto = getFirstArtistDto();

        assertFalse(oneArtistDto.name.isEmpty(), "First artist retrieved does not have any name.");
    }

    private static List<ArtistDto> getArtistDtoCollectionFromApiArtistServer() {
        return apiArtist.getArtistsDto();
    }

    private ArtistDto getFirstArtistDto() {
        return getArtistDtoCollectionFromApiArtistServer().get(0);
    }
}