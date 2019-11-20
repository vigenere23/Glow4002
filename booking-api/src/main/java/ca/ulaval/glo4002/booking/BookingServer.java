package ca.ulaval.glo4002.booking;

import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenHistoryMapper;
import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenInventoryMapper;
import ca.ulaval.glo4002.booking.api.resources.passOrder.dto.PassOrderResponseMapper;
import ca.ulaval.glo4002.booking.api.resources.program.dto.ProgramMapper;
import ca.ulaval.glo4002.booking.api.resources.transport.dto.ShuttleMapper;
import ca.ulaval.glo4002.booking.application.use_cases.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.application.use_cases.PassOrderUseCase;
import ca.ulaval.glo4002.booking.application.use_cases.ProfitUseCase;
import ca.ulaval.glo4002.booking.application.use_cases.TransportUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.application.use_cases.OxygenUseCase;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassPriceFactory;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.profit.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;
import ca.ulaval.glo4002.booking.domain.profit.ProfitSaver;
import ca.ulaval.glo4002.booking.application.use_cases.ProgramUseCase;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleFactory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleFiller;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapProfitRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapShuttleRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ExternalApiArtist;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumberFactory;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscountLinker;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrdererFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrdererLinker;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettingsFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplierFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.passes.passNumber.PassNumberFactory;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dto.ArtistInformationMapper;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassRepository;

public class BookingServer implements Runnable {
    private static final int PORT = 8181;
    private OxygenRequester oxygenRequester;
    private TransportReserver transportReserver;
    private ArtistRepository artistsRepository;
    private PassRepository passRepository;
    private IncomeSaver incomeSaver;
    private OutcomeSaver outcomeSaver;

    public static void main(String[] args) {
        new BookingServer().run();
    }

    public void run() {

        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
        ResourceConfig packageConfig = generateResourceConfig();
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

    public ResourceConfig generateResourceConfig() {
        Glow4002Dates festivalDates = new Glow4002Dates();
        
        ProfitUseCase profitUseCase = createProfitUseCase();        
        OxygenUseCase oxygenUseCase = createOxygenUseCase(festivalDates);
        TransportUseCase transportUseCase = createTransportUseCase(festivalDates);
        PassOrderUseCase passOrderUseCase = createPassOrderUseCase(festivalDates);
        ArtistRankingUseCase artistRankingUseCase = createArtistRankingUseCase();  
        ProgramUseCase programUseCase = createProgramUseCase();  
        ProgramValidator programValidator = new ProgramValidator(festivalDates);

        OxygenInventoryMapper oxygenInventoryMapper = new OxygenInventoryMapper();
        OxygenHistoryMapper oxygenHistoryMapper = new OxygenHistoryMapper();
        PassOrderResponseMapper passOrderResponseMapper = new PassOrderResponseMapper();
        ProgramMapper programMapper = new ProgramMapper();
        ShuttleMapper shuttleMapper = new ShuttleMapper();

        return new ResourceConfiguration(
            profitUseCase,
            passOrderUseCase,
            transportUseCase,
            oxygenUseCase,
            artistRankingUseCase,
            programUseCase,
            programValidator,
            oxygenInventoryMapper,
            oxygenHistoryMapper,
            passOrderResponseMapper,
            programMapper,
            shuttleMapper
        ).packages("ca.ulaval.glo4002.booking");
    }

    private ProfitUseCase createProfitUseCase() {
        ProfitRepository profitRepository = new HeapProfitRepository();
        ProfitCalculator profitCalculator = new ProfitCalculator();
        incomeSaver = new ProfitSaver(profitRepository);
        outcomeSaver = new ProfitSaver(profitRepository);
        return new ProfitUseCase(profitCalculator, profitRepository); 
    }

    private OxygenUseCase createOxygenUseCase(Glow4002Dates festivalDates) {
        OxygenInventoryRepository oxygenInventory = new HeapOxygenInventoryRepository();
        OxygenHistoryRepository oxygenHistory = new HeapOxygenHistoryRepository();
        OxygenOrdererLinker oxygenOrdererLinker = new OxygenOrdererLinker();
        OxygenRequestSettingsFactory requestSettingsFactory = new OxygenRequestSettingsFactory();
        OxygenSupplierFactory oxygenSupplierFactory = new OxygenSupplierFactory(oxygenHistory, outcomeSaver);
        OxygenOrdererFactory oxygenOrdererFactory = new OxygenOrdererFactory(oxygenOrdererLinker, oxygenSupplierFactory, requestSettingsFactory, oxygenInventory);
        oxygenRequester = new OxygenRequester(oxygenOrdererFactory, festivalDates.getOxygenLimitDeliveryDate());
        return new OxygenUseCase(oxygenHistory, oxygenInventory);
    }

    private TransportUseCase createTransportUseCase(FestivalDates festivalDates) {
        ShuttleRepository shuttleRepository = new HeapShuttleRepository();
        ShuttleFactory shuttleFactory = new ShuttleFactory();
        ShuttleFiller shuttleFiller = new ShuttleFiller(shuttleFactory, outcomeSaver);
        transportReserver = new TransportReserver(shuttleRepository, shuttleFiller);
        return new TransportUseCase(festivalDates, shuttleRepository);
    }

    private PassOrderUseCase createPassOrderUseCase(FestivalDates festivalDates) {
        passRepository = new HeapPassRepository();
        PassOrderRepository passOrderRepository = new HeapPassOrderRepository();
        PassPriceFactory passPriceFactory = new PassPriceFactory();
        PassNumberFactory passNumberFactory = new PassNumberFactory(new AtomicLong(0));
        PassFactory passFactory = new PassFactory(festivalDates, passNumberFactory, passPriceFactory);
        OrderNumberFactory orderNumberFactory = new OrderNumberFactory(new AtomicLong(0));
		OrderDiscountLinker orderDiscountFactory = new OrderDiscountLinker();
        PassOrderFactory passOrderFactory = new PassOrderFactory(festivalDates, passFactory, orderDiscountFactory, orderNumberFactory);
        
        return new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReserver, oxygenRequester, passRepository, incomeSaver);
    }

    private ArtistRankingUseCase createArtistRankingUseCase() {
        ArtistInformationMapper artistInformationMapper = new ArtistInformationMapper();
        ExternalApiArtist externalApiArtist = new ExternalApiArtist();
        artistsRepository = new ExternalArtistRepository(artistInformationMapper, externalApiArtist);
        ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
        return new ArtistRankingUseCase(artistsRepository, artistRankingFactory);
    }

    private ProgramUseCase createProgramUseCase() {
        return new ProgramUseCase(transportReserver, oxygenRequester, artistsRepository, passRepository, outcomeSaver);
    }
}
