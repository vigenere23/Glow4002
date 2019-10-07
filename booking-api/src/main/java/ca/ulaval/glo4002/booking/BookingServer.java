package ca.ulaval.glo4002.booking;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.persistance.heap.HeapRepository;
import ca.ulaval.glo4002.booking.services.orchestrators.PassOrderingOrchestrator;
import ca.ulaval.glo4002.booking.services.orders.PassOrderService;
import ca.ulaval.glo4002.booking.services.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.services.transport.TransportExposer;
import ca.ulaval.glo4002.booking.services.transport.TransportRequester;

public class BookingServer implements Runnable {
    private static final int PORT = 8181;

    public static void main(String[] args) {
        new BookingServer().run();
    }

    public void run() {

        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
        HeapRepository repository = new HeapRepository();
        Glow4002 festival = new Glow4002();
        OxygenRequester oxygenRequester = new OxygenRequester(festival.getStartDate().minusDays(1), repository.getOxygenPersistance());   
        TransportExposer transportExposer = new TransportRequester(repository.getShuttlePersistance(), festival);
        PassOrderService passOrderService = new PassOrderService(repository, festival);
        PassOrderingOrchestrator orchestrator = new PassOrderingOrchestrator(transportExposer, oxygenRequester, passOrderService);

        ResourceConfig packageConfig = new ResourceConfiguration(repository, oxygenRequester, transportExposer, passOrderService, orchestrator).packages("ca.ulaval.glo4002.booking");
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
}
