package ca.ulaval.glo4002.booking;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrderService;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenRequester;
import ca.ulaval.glo4002.booking.persistance.heap.HeapRepository;

public class BookingServer implements Runnable {
    private static final int PORT = 8181;

    public static void main(String[] args) {
        new BookingServer().run();
    }

    public void run() {

        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
        HeapRepository repository = new HeapRepository();
        Glow4002 festival = new Glow4002(repository);
        OxygenRequester oxygenExposer = new OxygenRequester(festival.getEndDate(), repository.getOxygenPersistance());
        PassOrderService passOrderService = new PassOrderService(repository, festival);
        festival.setOxygenRequester(oxygenExposer);
        ResourceConfig packageConfig = new ResourceConfiguration(repository, oxygenExposer, passOrderService).packages("ca.ulaval.glo4002.booking");
        ServletContainer container = new ServletContainer(packageConfig);
        ServletHolder servletHolder = new ServletHolder(container);

        contextHandler.addServlet(servletHolder, "/*");

        //for test purpose
        festival.orderTemporaryOxygenToValidateIfItWorks();

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
