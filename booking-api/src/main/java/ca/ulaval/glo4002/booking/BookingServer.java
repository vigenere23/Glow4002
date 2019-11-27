package ca.ulaval.glo4002.booking;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.booking.context.BookingContext;

public class BookingServer implements Runnable {
    private static final int PORT = 8181;

    public static void main(String[] args) {
        new BookingServer().run();
    }

    public void run() {

        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
        ResourceConfig resourceConfig = generateResourceConfig();
        ServletContainer container = new ServletContainer(resourceConfig);
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

    public ResourceConfig generateResourceConfig() {
		return new ResourceConfig()
				.packages("ca.ulaval.glo4002.booking.interfaces")
                .register(getRootBinder());
    }

    private AbstractBinder getRootBinder() {
		return new AbstractBinder() {
			@Override
			protected void configure() {
				install(new BookingContext());
			}
		};
    }
}
