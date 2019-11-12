package ca.ulaval.glo4002.booking;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.booking.application.*;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumberFactory;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.*;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassNumberFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassPriceFactory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistRankingInformationMapper;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalApiArtist;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.*;


public class BookingServer implements Runnable {
    private static final int PORT = 8181;
    private ExternalApiArtist externalApiArtist;

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
            try {
                externalApiArtist.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            server.destroy();
        }
    }

    private ResourceConfig setupResourceConfig() {
        FestivalDates festivalDates = new Glow4002Dates();

        OxygenInventoryRepository oxygenInventoryRepository = new HeapOxygenInventoryRepository();
        OxygenHistoryRepository oxygenHistoryRepository = new HeapOxygenHistoryRepository();
        OxygenOrderFactory oxygenOrderFactory = new OxygenOrderFactory(festivalDates.getStartDate().minusDays(1));
        OxygenReserver oxygenReserver = new OxygenReserver(oxygenOrderFactory, oxygenInventoryRepository, oxygenHistoryRepository);
        OxygenUseCase oxygenUseCase = new OxygenUseCase(oxygenHistoryRepository, oxygenInventoryRepository);

        ShuttleRepository shuttleRepository = new HeapShuttleRepository();
        TransportReserver transportReserver = new TransportReserver(shuttleRepository);
        TransportUseCase transportUseCase = new TransportUseCase(festivalDates, shuttleRepository);

        PassPriceFactory passPriceFactory = new PassPriceFactory();
        PassNumberFactory passNumberFactory = new PassNumberFactory(new AtomicLong(0));
        PassFactory passFactory = new PassFactory(festivalDates, passNumberFactory, passPriceFactory);
        
        OrderNumberFactory orderNumberFactory = new OrderNumberFactory(new AtomicLong(0));
        PassOrderRepository passOrderRepository = new HeapPassOrderRepository();
        PassOrderFactory passOrderFactory = new PassOrderFactory(festivalDates, orderNumberFactory, passFactory);
        PassOrderUseCase passOrderUseCase = new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReserver, oxygenReserver);

        ArtistRankingInformationMapper artistRankingInformationMapper = new ArtistRankingInformationMapper();
        externalApiArtist = new ExternalApiArtist();
        ArtistRepository artistsRepository = new ExternalArtistRepository(artistRankingInformationMapper, externalApiArtist);
        ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
        ArtistRankingUseCase artistRankingUseCase = new ArtistRankingUseCase(artistsRepository, artistRankingFactory);

        return new ResourceConfiguration(
            passOrderUseCase,
            transportUseCase,
            oxygenUseCase,
            artistRankingUseCase
        ).packages("ca.ulaval.glo4002.booking");
    }
}
