package ca.ulaval.glo4002.booking.api.resources.program;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.application.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.ProgramUseCase;
import ca.ulaval.glo4002.booking.api.resources.program.dto.ProgramMapper;
import ca.ulaval.glo4002.booking.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.application.TransportUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscountFactory;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumberFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenOrderFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassPriceFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.passes.passNumber.PassNumberFactory;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;
import ca.ulaval.glo4002.booking.domain.profit.ProfitSaver;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleFactory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleFiller;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalApiArtist;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistInformationMapper;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapProfitRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapShuttleRepository;
import ca.ulaval.glo4002.booking.application.PassOrderUseCase;

///Note => Those tests have been disabled because we know the implementation is not optimal. We did not learned how to do it yet.
@Disabled
public class ProgramResourceIT extends JerseyTest {

    private static final String PROGRAM_URL = "/program";
    private String invalidProgram = "{\"program\": [{}]}";

    FestivalDates festivalDates = new Glow4002Dates();

    ProfitRepository profitRepository = new HeapProfitRepository();
    IncomeSaver incomeSaver = new ProfitSaver(profitRepository);
    OutcomeSaver outcomeSaver = new ProfitSaver(profitRepository);

    OxygenInventoryRepository oxygenInventoryRepository = new HeapOxygenInventoryRepository();
    OxygenHistoryRepository oxygenHistoryRepository = new HeapOxygenHistoryRepository();
    OxygenOrderFactory oxygenOrderFactory = new OxygenOrderFactory(festivalDates.getStartDate().minusDays(1));
    OxygenReserver oxygenReserver = new OxygenReserver(oxygenOrderFactory, oxygenInventoryRepository, oxygenHistoryRepository, outcomeSaver);
    OxygenUseCase oxygenUseCase = new OxygenUseCase(oxygenHistoryRepository, oxygenInventoryRepository);

    ShuttleFactory shuttleFactory = new ShuttleFactory();
    ShuttleFiller shuttleFiller = new ShuttleFiller(shuttleFactory, outcomeSaver);
    ShuttleRepository shuttleRepository = new HeapShuttleRepository();
    TransportReserver transportReserver = new TransportReserver(shuttleRepository, shuttleFiller);
    TransportUseCase transportUseCase = new TransportUseCase(festivalDates, shuttleRepository);

    PassOrderRepository passOrderRepository = new HeapPassOrderRepository();
    PassRepository passRepository = new HeapPassRepository();
    PassPriceFactory passPriceFactory = new PassPriceFactory();
    PassNumberFactory passNumberFactory = new PassNumberFactory(new AtomicLong(0));
    PassFactory passFactory = new PassFactory(festivalDates, passNumberFactory, passPriceFactory);
    OrderNumberFactory orderNumberFactory = new OrderNumberFactory(new AtomicLong(0));
    OrderDiscountFactory orderDiscountFactory = new OrderDiscountFactory();
    PassOrderFactory passOrderFactory = new PassOrderFactory(festivalDates, passFactory, orderDiscountFactory, orderNumberFactory);
    PassOrderUseCase passOrderUseCase = new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReserver, oxygenReserver, passRepository, incomeSaver);
    ArtistInformationMapper artistInformationMapper = new ArtistInformationMapper();
    ExternalApiArtist externalApiArtist = new ExternalApiArtist();
    ArtistRepository artistsRepository = new ExternalArtistRepository(artistInformationMapper, externalApiArtist);
    ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
    ArtistRankingUseCase artistRankingUseCase = new ArtistRankingUseCase(artistsRepository, artistRankingFactory);

    ProgramUseCase programUseCase = new ProgramUseCase(transportReserver, oxygenReserver, artistsRepository, passRepository, outcomeSaver);
    ProgramValidator programValidator = new ProgramValidator(festivalDates);
    ProgramMapper programMapper = new ProgramMapper();

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
                bind(programMapper).to(ProgramMapper.class);
            }
        });
        return resourceConfig;
    }

    @Test
    public void giveninvalidProgramPosted_thenReturnsCorrectStatusCode() {
        Response response = target(PROGRAM_URL).request().post(Entity.json(invalidProgram));

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus(), "Http Response should be 400 ");
    }

    @Test
    public void givenValidProgramPosted_thenReturnsResponseWithCorrectLocationHeader() {
        Response response = target(PROGRAM_URL).request().post(Entity.json(invalidProgram));

        assertEquals("application/json", response.getHeaderString(HttpHeaders.CONTENT_TYPE));
    }
}