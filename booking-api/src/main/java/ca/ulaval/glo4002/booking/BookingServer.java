package ca.ulaval.glo4002.booking;

import ca.ulaval.glo4002.booking.domain.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistRankingInformationMapper;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ApiArtistRepository;
import ca.ulaval.glo4002.booking.domain.application.OxygenUseCase;
import ca.ulaval.glo4002.booking.domain.oxygen.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapShuttleRepository;

public class BookingServer implements Runnable {
    private static final int PORT = 8181;

    public static void main(String[] args) {
        new BookingServer().run();
    }

    public void run() {

        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
        ResourceConfig packageConfig = setupResourceConfig();
        ServletContainer container = new ServletContainer(packageConfig);
        ServletHolder servletHolder = new ServletHolder(container);

        contextHandler.addServlet(servletHolder, "/*");

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.destroy();
        }
    }

    private ResourceConfig setupResourceConfig() {
        FestivalDates festival = new Glow4002Dates();

        PassOrderRepository passOrderRepository = new HeapPassOrderRepository();
        OxygenInventoryRepository oxygenInventoryRepository = new HeapOxygenInventoryRepository();
        OxygenHistoryRepository oxygenHistoryRepository = new HeapOxygenHistoryRepository();
        ShuttleRepository shuttleRepository = new HeapShuttleRepository();

        OxygenFactory oxygenFactory = new OxygenFactory(festival.getStartDate().minusDays(1));
        OxygenProducer oxygenProducer = new OxygenProducer(oxygenFactory);
        TransportRequester transportRequester = new TransportRequester(shuttleRepository, festival);
        PassOrderFactory passOrderFactory = new PassOrderFactory(festival);
        PassOrderUseCase passOrderUseCase = new PassOrderUseCase(transportRequester, oxygenProducer, passOrderFactory, passOrderRepository, oxygenInventoryRepository, oxygenHistoryRepository);
        OxygenUseCase oxygenUseCase = new OxygenUseCase(oxygenHistoryRepository, oxygenInventoryRepository);

        ArtistRankingInformationMapper artistRankingInformationMapper = new ArtistRankingInformationMapper();
        ArtistRepository artistsRepository = new ApiArtistRepository(artistRankingInformationMapper);
        ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
        ArtistRankingUseCase artistRankingUseCase = new ArtistRankingUseCase(artistsRepository, artistRankingFactory);

        return new ResourceConfiguration(
                oxygenProducer,
                transportRequester,
                passOrderFactory,
                passOrderUseCase,
                oxygenUseCase,
                artistRankingUseCase
        ).packages("ca.ulaval.glo4002.booking");
    }
}
