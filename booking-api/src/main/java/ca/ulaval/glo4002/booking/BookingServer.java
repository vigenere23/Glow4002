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
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenOrderFactory;
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
    private OxygenReserver oxygenReserver;
    private TransportReserver transportReserver;
    private ArtistRepository artistsRepository;
    private PassRepository passRepository;

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
        Glow4002Dates festivalDates = new Glow4002Dates();

        OxygenUseCase oxygenUseCase = createOxygenUseCase(festivalDates);
        TransportUseCase transportUseCase = createTransportUseCase(festivalDates);
        PassOrderUseCase passOrderUseCase = createPassOrderUseCase(festivalDates);
        ArtistRankingUseCase artistRankingUseCase = createArtistRankingUseCase();  
        ProgramUseCase programUseCase = createProgramUseCase();              
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

    private OxygenUseCase createOxygenUseCase(Glow4002Dates festivalDates) {
        OxygenInventoryRepository oxygenInventoryRepository = new HeapOxygenInventoryRepository();
        OxygenHistoryRepository oxygenHistoryRepository = new HeapOxygenHistoryRepository();
        OxygenOrderFactory oxygenOrderFactory = new OxygenOrderFactory(festivalDates.getOxygenLimitDeliveryDate());
        oxygenReserver = new OxygenReserver(oxygenOrderFactory, oxygenInventoryRepository, oxygenHistoryRepository);
        return new OxygenUseCase(oxygenHistoryRepository, oxygenInventoryRepository);
    }

    private TransportUseCase createTransportUseCase(FestivalDates festivalDates) {
        ShuttleRepository shuttleRepository = new HeapShuttleRepository();
        transportReserver = new TransportReserver(shuttleRepository);
        return new TransportUseCase(festivalDates, shuttleRepository);
    }

    private PassOrderUseCase createPassOrderUseCase(FestivalDates festivalDates) {
        PassOrderRepository passOrderRepository = new HeapPassOrderRepository();
        passRepository = new HeapPassRepository();
        PassPriceFactory passPriceFactory = new PassPriceFactory();
        PassFactory passFactory = new PassFactory(festivalDates, passPriceFactory);
        PassOrderFactory passOrderFactory = new PassOrderFactory(festivalDates, passFactory);
        return new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReserver, oxygenReserver, passRepository);
    }

    private ArtistRankingUseCase createArtistRankingUseCase() {
        ArtistInformationMapper artistInformationMapper = new ArtistInformationMapper();
        externalApiArtist = new ExternalApiArtist();
        artistsRepository = new ExternalArtistRepository(artistInformationMapper, externalApiArtist);
        ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
        return new ArtistRankingUseCase(artistsRepository, artistRankingFactory);
    }

    private ProgramUseCase createProgramUseCase() {
        FestivalAttendeesCounter festivalAttendeesCounter = new FestivalAttendeesCounter();
        return new ProgramUseCase(transportReserver, oxygenReserver, artistsRepository, passRepository, festivalAttendeesCounter);
    }
}
