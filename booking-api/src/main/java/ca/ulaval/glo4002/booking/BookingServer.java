package ca.ulaval.glo4002.booking;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.booking.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.application.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.application.ProgramUseCase;
import ca.ulaval.glo4002.booking.application.TransportUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.passes.FestivalAttendeesCounter;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassPriceFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalApiArtist;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistInformationMapper;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapShuttleRepository;

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
        OxygenFactory oxygenFactory = new OxygenFactory(festivalDates.getStartDate().minusDays(1));
        OxygenReserver oxygenReserver = new OxygenReserver(oxygenFactory, oxygenInventoryRepository, oxygenHistoryRepository);
        OxygenUseCase oxygenUseCase = new OxygenUseCase(oxygenHistoryRepository, oxygenInventoryRepository);

        ShuttleRepository shuttleRepository = new HeapShuttleRepository();
        TransportReserver transportReserver = new TransportReserver(shuttleRepository);
        TransportUseCase transportUseCase = new TransportUseCase(festivalDates, shuttleRepository);

        PassOrderRepository passOrderRepository = new HeapPassOrderRepository();
        PassRepository passRepository = new HeapPassRepository();
        PassPriceFactory passPriceFactory = new PassPriceFactory();
        PassFactory passFactory = new PassFactory(festivalDates, passPriceFactory);
        PassOrderFactory passOrderFactory = new PassOrderFactory(festivalDates, passFactory);
        PassOrderUseCase passOrderUseCase = new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReserver, oxygenReserver, passRepository);

        ArtistInformationMapper artistInformationMapper = new ArtistInformationMapper();
        externalApiArtist = new ExternalApiArtist();
        ArtistRepository artistsRepository = new ExternalArtistRepository(artistInformationMapper, externalApiArtist);
        ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
        ArtistRankingUseCase artistRankingUseCase = new ArtistRankingUseCase(artistsRepository, artistRankingFactory);

        FestivalAttendeesCounter festivalAttendeesCounter = new FestivalAttendeesCounter();
        ProgramUseCase programUseCase = new ProgramUseCase(transportReserver, oxygenReserver, artistsRepository, passRepository, festivalAttendeesCounter);
        ProgramValidator programValidator = new ProgramValidator(festivalDates);

        return new ResourceConfiguration(
            passOrderUseCase,
            transportUseCase,
            oxygenUseCase,
            artistRankingUseCase,
            programUseCase,
            programValidator
        ).packages("ca.ulaval.glo4002.booking");
    }
}
