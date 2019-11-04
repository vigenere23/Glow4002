package ca.ulaval.glo4002.booking;

import ca.ulaval.glo4002.booking.domain.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.application.TransportUseCase;
import ca.ulaval.glo4002.booking.domain.orders.PassUtilities;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistRankingInformationMapper;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ApiArtistRepository;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReservation;
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
        Glow4002 festival = new Glow4002();

        PassOrderRepository passOrderRepository = new HeapPassOrderRepository();
        OxygenHistoryRepository oxygenHistoryRepository = new HeapOxygenHistoryRepository();
        OxygenInventoryRepository oxygenInventoryRepository = new HeapOxygenInventoryRepository();
        ShuttleRepository shuttleRepository = new HeapShuttleRepository();

        OxygenRequester oxygenRequester = new OxygenRequester(festival.getStartDate().minusDays(1), oxygenHistoryRepository, oxygenInventoryRepository);
        TransportReservation transportReservation = new TransportReservation();
        PassOrderFactory passOrderFactory = new PassOrderFactory(festival);
        PassUtilities passUtilities = new PassUtilities(transportReservation, oxygenRequester, passOrderFactory);
        PassOrderUseCase passOrderUseCase = new PassOrderUseCase(passUtilities, passOrderRepository, shuttleRepository);
        TransportUseCase transportUseCase = new TransportUseCase(festival, shuttleRepository);

        ArtistRankingInformationMapper artistRankingInformationMapper = new ArtistRankingInformationMapper();
        ArtistRepository artistsRepository = new ApiArtistRepository(artistRankingInformationMapper);
        ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
        ArtistRankingUseCase artistRankingUseCase = new ArtistRankingUseCase(artistsRepository, artistRankingFactory);

        return new ResourceConfiguration(
                oxygenRequester,
                transportUseCase,
                passOrderFactory,
                passOrderUseCase,
                artistRankingUseCase
        ).packages("ca.ulaval.glo4002.booking");
    }
}
