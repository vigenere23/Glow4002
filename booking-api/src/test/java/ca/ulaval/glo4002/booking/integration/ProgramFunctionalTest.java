package ca.ulaval.glo4002.booking.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.application.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.ProgramUseCase;
import ca.ulaval.glo4002.booking.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.application.TransportUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.passes.PassCounter;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassPriceFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalApiArtist;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistInformationMapper;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapShuttleRepository;
import ca.ulaval.glo4002.booking.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.api.resources.ProgramResource;

public class ProgramFunctionalTest extends JerseyTest {

    private static final String PROGRAM_URL = "/program";

    FestivalDates festivalDates = new Glow4002Dates();

    OxygenInventoryRepository oxygenInventoryRepository = new HeapOxygenInventoryRepository();
    OxygenHistoryRepository oxygenHistoryRepository = new HeapOxygenHistoryRepository();
    OxygenFactory oxygenFactory = new OxygenFactory(festivalDates.getStartDate().minusDays(1));
    OxygenReserver oxygenReserver = new OxygenReserver(oxygenFactory, oxygenInventoryRepository, oxygenHistoryRepository);
    OxygenUseCase oxygenUseCase = new OxygenUseCase(oxygenHistoryRepository, oxygenInventoryRepository);

    ShuttleRepository shuttleRepository = new HeapShuttleRepository();
    TransportReserver transportReserver = new TransportReserver(shuttleRepository);
    TransportUseCase transportUseCase = new TransportUseCase(festivalDates, shuttleRepository);

    PassOrderRepository passOrderRepository = new HeapPassOrderRepository();
    PassRepository passRepository = new HeapPassRepository();
    PassPriceFactory passPriceFactory = new PassPriceFactory();
    PassFactory passFactory = new PassFactory(festivalDates, passPriceFactory);
    PassOrderFactory passOrderFactory = new PassOrderFactory(festivalDates, passFactory);
    PassOrderUseCase passOrderUseCase = new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReserver, oxygenReserver, passRepository);

    ArtistInformationMapper artistInformationMapper = new ArtistInformationMapper();
    ExternalApiArtist externalApiArtist = new ExternalApiArtist();
    ArtistRepository artistsRepository = new ExternalArtistRepository(artistInformationMapper, externalApiArtist);
    ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
    ArtistRankingUseCase artistRankingUseCase = new ArtistRankingUseCase(artistsRepository, artistRankingFactory);

    PassCounter passCounter = new PassCounter();
    ProgramUseCase programUseCase = new ProgramUseCase(transportReserver, oxygenReserver, artistsRepository, passRepository, passCounter);
    ProgramValidator programValidator = new ProgramValidator(festivalDates);

    String validProgram = "{\"program\": [{   \"eventDate\": \"2050-07-17\",   \"am\": \"yoga\",   \"pm\": \"Kid Rocket\"},{   \"eventDate\": \"2050-07-18\",   \"am\": \"yoga\",   \"pm\": \"Freddie Mercury\"},{   \"eventDate\": \"2050-07-19\",   \"am\": \"cardio\",   \"pm\": \"Kelvin Harris\"},{   \"eventDate\": \"2050-07-20\",   \"am\": \"cardio\",   \"pm\": \"Lady Gamma\"},{   \"eventDate\": \"2050-07-21\",   \"am\": \"yoga\",   \"pm\": \"30 Seconds to Mars\"},{   \"eventDate\": \"2050-07-22\",   \"am\": \"yoga\",   \"pm\": \"Coldray\"},{   \"eventDate\": \"2050-07-23\",   \"am\": \"cardio\",   \"pm\": \"Suns N’ Roses\"},{   \"eventDate\": \"2050-07-24\",   \"am\": \"yoga\",   \"pm\": \"Eclipse Presley\"}]}";
    String invalidProgram = "{\"program\": [{}]}";
    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected Application configure() {

        ResourceConfig resourceConfig = new ResourceConfig(ProgramResource.class).packages("ca.ulaval.glo4002.booking");
        resourceConfig.register(new AbstractBinder() {

            @Override
            protected void configure() {
                bind(transportUseCase).to(TransportUseCase.class);
                bind(artistRankingUseCase).to(ArtistRankingUseCase.class);
                bind(passOrderUseCase).to(PassOrderUseCase.class);
                bind(oxygenUseCase).to(OxygenUseCase.class);
                bind(programUseCase).to(ProgramUseCase.class);
                bind(programValidator).to(ProgramValidator.class);
            }
        });
        return resourceConfig;
    }

    private Response postProgram(String program) {
        Response response = target(PROGRAM_URL).request().post(Entity.json(program));
        return response;
    }

    @Test
    public void givenValidProgramPosted_thenReturnsCorrectStatusCode() {
        Response response = postProgram(validProgram);

        assertEquals(Status.OK.getStatusCode(), response.getStatus(), "Http Response should be 200 ");
    }

    @Test
    public void giveninvalidProgramPosted_thenReturnsCorrectStatusCode() {
        Response response = postProgram(invalidProgram);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus(), "Http Response should be 400 ");
    }
}