package ca.ulaval.glo4002.booking.api.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.api.resources.oxygen.ReportOxygenResources;
import ca.ulaval.glo4002.booking.api.resources.passOrder.OrdersResource;
import ca.ulaval.glo4002.booking.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.application.TransportUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassPriceFactory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalApiArtist;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistRankingInformationMapper;
import com.google.gson.Gson;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.application.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapShuttleRepository;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class MockedBookingServer extends JerseyTest {
    protected static final String ORDERS_URL = "/orders";

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
    PassPriceFactory passPriceFactory = new PassPriceFactory();
    PassFactory passFactory = new PassFactory(festivalDates, passPriceFactory);
    PassOrderFactory passOrderFactory = new PassOrderFactory(festivalDates, passFactory);
    PassOrderUseCase passOrderUseCase = new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReserver, oxygenReserver);
    ArtistRankingInformationMapper artistRankingInformationMapper = new ArtistRankingInformationMapper();
    ExternalApiArtist externalApiArtist = new ExternalApiArtist();
    ArtistRepository artistsRepository = new ExternalArtistRepository(artistRankingInformationMapper, externalApiArtist);
    ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
    ArtistRankingUseCase artistRankingUseCase = new ArtistRankingUseCase(artistsRepository, artistRankingFactory);

    static
    {
        System.setProperty("jersey.config.test.container.port", "8181");
    }

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
        ResourceConfig resourceConfig = new ResourceConfig(ReportOxygenResources.class, OrdersResource.class).packages("ca.ulaval.glo4002.booking");
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(passOrderUseCase).to(PassOrderUseCase.class);
                bind(transportUseCase).to(TransportUseCase.class);
                bind(oxygenUseCase).to(OxygenUseCase.class);
                bind(artistRankingUseCase).to(ArtistRankingUseCase.class);
            }
        });
        return resourceConfig;
    }

    protected Response postSinglePassOrder(String orderDate, String passCategory, List<String> eventDates) {
        return postPassOrderWithEventDates(orderDate, passCategory, eventDates, "singlePass");
    }

    protected Response postPackagePassOrder(String orderDate, String passCategory) {
        return target(ORDERS_URL).request().post(Entity.json("{\"orderDate\":\"" + orderDate + "\",\"vendorCode\":\"TEAM\",\"passes\":"+
                "{\"passCategory\":\"" + passCategory + "\",\"passOption\":\"package\"}}"));
    }

    protected Response postPassOrderWithEventDates(String orderDate, String passCategory, List<String> eventDates, String passOption) {
        String jsonEventDates = new Gson().toJson(eventDates);
        return target(ORDERS_URL).request().post(Entity.json("{\"orderDate\":\"" + orderDate + "\",\"vendorCode\":\"TEAM\",\"passes\":"+
                "{\"passCategory\":\"" + passCategory + "\",\"passOption\":\"" + passOption + "\",\"eventDates\":" + jsonEventDates + "}}"));
    }
}
