package ca.ulaval.glo4002.booking;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.booking.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.application.ProfitUseCase;
import ca.ulaval.glo4002.booking.application.TransportUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.application.OxygenUseCase;
import ca.ulaval.glo4002.booking.domain.oxygen.*;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassPriceFactory;
import ca.ulaval.glo4002.booking.domain.profit.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleFiller;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapProfitRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapShuttleRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistRankingInformationMapper;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalApiArtist;


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

        ProfitRepository profitRepository = new HeapProfitRepository();
        ProfitCalculator profitCalculator = new ProfitCalculator(profitRepository) ;
        ProfitUseCase profitUseCase = new ProfitUseCase(profitCalculator);

        OxygenInventoryRepository oxygenInventoryRepository = new HeapOxygenInventoryRepository();
        OxygenHistoryRepository oxygenHistoryRepository = new HeapOxygenHistoryRepository();
        OxygenFactory oxygenFactory = new OxygenFactory(festivalDates.getStartDate().minusDays(1));
        OxygenReserver oxygenReserver = new OxygenReserver(oxygenFactory, oxygenInventoryRepository, oxygenHistoryRepository);
        OxygenUseCase oxygenUseCase = new OxygenUseCase(oxygenHistoryRepository, oxygenInventoryRepository);

        ShuttleFiller shuttleFiller = new ShuttleFiller(profitCalculator); 
        ShuttleRepository shuttleRepository = new HeapShuttleRepository();
        TransportReserver transportReserver = new TransportReserver(shuttleRepository, shuttleFiller);
        TransportUseCase transportUseCase = new TransportUseCase(festivalDates, shuttleRepository);

        PassOrderRepository passOrderRepository = new HeapPassOrderRepository();
        PassPriceFactory passPriceFactory = new PassPriceFactory();
        PassFactory passFactory = new PassFactory(festivalDates, passPriceFactory);
        PassOrderFactory passOrderFactory = new PassOrderFactory(festivalDates, passFactory);
        PassOrderUseCase passOrderUseCase = new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReserver, oxygenReserver, profitCalculator);
        ArtistRankingInformationMapper artistRankingInformationMapper = new ArtistRankingInformationMapper();
        externalApiArtist = new ExternalApiArtist();
        ArtistRepository artistsRepository = new ExternalArtistRepository(artistRankingInformationMapper, externalApiArtist);
        ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
        ArtistRankingUseCase artistRankingUseCase = new ArtistRankingUseCase(artistsRepository, artistRankingFactory);

        return new ResourceConfiguration(
            passOrderUseCase,
            transportUseCase,
            oxygenUseCase,
            artistRankingUseCase,
            profitUseCase
        ).packages("ca.ulaval.glo4002.booking");
    }
}
