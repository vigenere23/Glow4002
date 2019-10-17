package ca.ulaval.glo4002.booking;

import ca.ulaval.glo4002.booking.api.dtoMappers.ArtistRankingInformationMapper;
import ca.ulaval.glo4002.booking.api.dtos.artists.ArtistExternalResponse;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ApiArtistRepository;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.orchestrators.PassOrderingOrchestrator;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRequester;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
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
        Glow4002 festival = new Glow4002();

        PassOrderRepository passOrderRepository = new HeapPassOrderRepository();
        OxygenHistoryRepository oxygenHistoryRepository = new HeapOxygenHistoryRepository();
        OxygenInventoryRepository oxygenInventoryRepository = new HeapOxygenInventoryRepository();
        ShuttleRepository shuttleRepository = new HeapShuttleRepository();
        ArtistRankingInformationMapper artistRankingInformationMapper = new ArtistRankingInformationMapper();

        OxygenRequester oxygenRequester = new OxygenRequester(festival.getStartDate().minusDays(1), oxygenHistoryRepository, oxygenInventoryRepository);
        TransportRequester transportRequester = new TransportRequester(shuttleRepository, festival);
        PassOrderRequester passOrderRequester = new PassOrderRequester(passOrderRepository, festival);
        ArtistExternalResponse artistExternalResponse = new ArtistExternalResponse(artistRankingInformationMapper);
        ArtistRepository artistsRepository = new ApiArtistRepository(artistExternalResponse);
        PassOrderingOrchestrator passOrderingOrchestrator = new PassOrderingOrchestrator(transportRequester, oxygenRequester, passOrderRequester, artistsRepository);

        ResourceConfig packageConfig = new ResourceConfiguration(
            oxygenRequester,
            transportRequester,
            passOrderRequester,
            passOrderingOrchestrator
        ).packages("ca.ulaval.glo4002.booking");

        return packageConfig;
    }
}
