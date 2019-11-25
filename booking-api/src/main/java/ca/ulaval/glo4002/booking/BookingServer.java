package ca.ulaval.glo4002.booking;

import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4002.booking.application.artists.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.application.dates.DatesUseCase;
import ca.ulaval.glo4002.booking.application.orders.PassOrderUseCase;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassDtoMapper;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDtoMapper;
import ca.ulaval.glo4002.booking.application.oxygen.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryEntryDtoMapper;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryEntryDtoMapper;
import ca.ulaval.glo4002.booking.application.profit.ProfitUseCase;
import ca.ulaval.glo4002.booking.application.program.ProgramUseCase;
import ca.ulaval.glo4002.booking.application.program.dtos.ProgramDayDtoMapper;
import ca.ulaval.glo4002.booking.application.transport.TransportUseCase;
import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDtoMapper;
import ca.ulaval.glo4002.booking.context.BookingContext;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.dates.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscountLinker;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumberFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrdererFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrdererLinker;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettingsFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplierFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassPriceFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumberFactory;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.profit.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;
import ca.ulaval.glo4002.booking.domain.profit.ProfitSaver;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleFactory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleFiller;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.ExternalApiArtist;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.ExternalApiArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistInformationMapper;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapProfitRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapShuttleRepository;

public class BookingServer implements Runnable {
    private static final int PORT = 8181;
    private OxygenRequester oxygenRequester;
    private TransportReserver transportReserver;
    private ArtistRepository artistsRepository;
    private PassRepository passRepository;
    private IncomeSaver incomeSaver;
    private OutcomeSaver outcomeSaver;
    private Glow4002Dates glow4002Dates;

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
				.packages("ca.ulaval.glo4002.booking.api")
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
        
    //     glow4002Dates = new Glow4002Dates();
        
    //     ProfitUseCase profitUseCase = createProfitUseCase();
    //     OxygenUseCase oxygenUseCase = createOxygenUseCase(glow4002Dates);
    //     TransportUseCase transportUseCase = createTransportUseCase(glow4002Dates);
    //     PassOrderUseCase passOrderUseCase = createPassOrderUseCase(glow4002Dates);
    //     ArtistRankingUseCase artistRankingUseCase = createArtistRankingUseCase();
    //     ProgramUseCase programUseCase = createProgramUseCase();
    //     DatesUseCase datesUseCase = createDatesUseCase();

    //     return new ResourceConfiguration(
    //         profitUseCase,
    //         passOrderUseCase,
    //         transportUseCase,
    //         oxygenUseCase,
    //         artistRankingUseCase,
    //         programUseCase,
    //         datesUseCase
    //     ).packages("ca.ulaval.glo4002.booking");
    // }

    // private ProfitUseCase createProfitUseCase() {
    //     ProfitRepository profitRepository = new HeapProfitRepository();
    //     ProfitCalculator profitCalculator = new ProfitCalculator();
    //     incomeSaver = new ProfitSaver(profitRepository);
    //     outcomeSaver = new ProfitSaver(profitRepository);
    //     return new ProfitUseCase(profitCalculator, profitRepository); 
    // }

    // private OxygenUseCase createOxygenUseCase(OxygenDates oxygenDates) {
    //     OxygenInventoryRepository oxygenInventoryRepository = new HeapOxygenInventoryRepository();
    //     OxygenHistoryRepository oxygenHistoryRepository = new HeapOxygenHistoryRepository();
    //     OxygenOrdererLinker oxygenOrdererLinker = new OxygenOrdererLinker();
    //     OxygenRequestSettingsFactory requestSettingsFactory = new OxygenRequestSettingsFactory();
    //     OxygenSupplierFactory oxygenSupplierFactory = new OxygenSupplierFactory(oxygenHistoryRepository, outcomeSaver);
    //     OxygenOrdererFactory oxygenOrdererFactory = new OxygenOrdererFactory(oxygenOrdererLinker, oxygenSupplierFactory, requestSettingsFactory, oxygenInventoryRepository);
    //     OxygenHistoryEntryDtoMapper oxygenHistoryMapper = new OxygenHistoryEntryDtoMapper();
    //     OxygenInventoryEntryDtoMapper oxygenInventoryMapper = new OxygenInventoryEntryDtoMapper();
    //     oxygenRequester = new OxygenRequester(oxygenOrdererFactory, oxygenDates);
    //     return new OxygenUseCase(oxygenHistoryRepository, oxygenInventoryRepository, oxygenHistoryMapper, oxygenInventoryMapper);
    // }

    // private TransportUseCase createTransportUseCase(FestivalDates festivalDates) {
    //     ShuttleRepository shuttleRepository = new HeapShuttleRepository();
    //     ShuttleFactory shuttleFactory = new ShuttleFactory();
    //     ShuttleFiller shuttleFiller = new ShuttleFiller(shuttleFactory, outcomeSaver);
    //     ShuttleDtoMapper shuttleDtoMapper = new ShuttleDtoMapper();
    //     transportReserver = new TransportReserver(shuttleRepository, shuttleFiller);
    //     return new TransportUseCase(festivalDates, shuttleRepository, shuttleDtoMapper);
    // }

    // private PassOrderUseCase createPassOrderUseCase(FestivalDates festivalDates) {
    //     passRepository = new HeapPassRepository();
    //     PassOrderRepository passOrderRepository = new HeapPassOrderRepository();
    //     PassPriceFactory passPriceFactory = new PassPriceFactory();
    //     PassNumberFactory passNumberFactory = new PassNumberFactory(new AtomicLong(0));
    //     PassFactory passFactory = new PassFactory(festivalDates, passNumberFactory, passPriceFactory);
    //     OrderNumberFactory orderNumberFactory = new OrderNumberFactory(new AtomicLong(0));
	// 	OrderDiscountLinker orderDiscountFactory = new OrderDiscountLinker();
    //     PassOrderFactory passOrderFactory = new PassOrderFactory(festivalDates, passFactory, orderDiscountFactory, orderNumberFactory);
    //     PassDtoMapper passDtoMapper = new PassDtoMapper();
    //     PassOrderDtoMapper passOrderDtoMapper = new PassOrderDtoMapper(passDtoMapper);

    //     return new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReserver, oxygenRequester, passRepository, incomeSaver, passOrderDtoMapper);
    // }

    // private ArtistRankingUseCase createArtistRankingUseCase() {
    //     ArtistInformationMapper artistInformationMapper = new ArtistInformationMapper();
    //     ExternalApiArtist externalApiArtist = new ExternalApiArtist();
    //     artistsRepository = new ExternalApiArtistRepository(artistInformationMapper, externalApiArtist);
    //     ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
    //     return new ArtistRankingUseCase(artistsRepository, artistRankingFactory);
    // }

    // private ProgramUseCase createProgramUseCase() {
    //     ProgramValidator programValidator = new ProgramValidator(glow4002Dates);
    //     ProgramDayDtoMapper programMapper = new ProgramDayDtoMapper();
    //     return new ProgramUseCase(transportReserver, oxygenRequester, artistsRepository, passRepository, outcomeSaver, programValidator, programMapper);
    // }

    // private DatesUseCase createDatesUseCase() {
    //     return new DatesUseCase(glow4002Dates);
    // }
}
