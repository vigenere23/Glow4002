package ca.ulaval.glo4002.booking.api.resources;

import javax.ws.rs.core.Application;

import ca.ulaval.glo4002.booking.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.application.TransportUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassPriceFactory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ApiArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.dtos.ArtistRankingInformationMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.application.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapShuttleRepository;

public class MockedBookingServer {
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
    PassPriceFactory passPriceFactory = new PassPriceFactory();
    PassFactory passFactory = new PassFactory(festivalDates, passPriceFactory);
    PassOrderFactory passOrderFactory = new PassOrderFactory(festivalDates, passFactory);
    PassOrderUseCase passOrderUseCase = new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReserver, oxygenReserver);

    ArtistRankingInformationMapper artistRankingInformationMapper = new ArtistRankingInformationMapper();
    ArtistRepository artistsRepository = new ApiArtistRepository(artistRankingInformationMapper);
    ArtistRankingFactory artistRankingFactory = new ArtistRankingFactory();
    ArtistRankingUseCase artistRankingUseCase = new ArtistRankingUseCase(artistsRepository, artistRankingFactory);

    static
    {
        System.setProperty("jersey.config.test.container.port", "8181");
    }

    public Application configure() {

        ResourceConfig resourceConfig = new ResourceConfig(ReportOxygenResources.class, OrdersResource.class).packages("ca.ulaval.glo4002.booking");
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(passOrderUseCase).to(PassOrderUseCase.class);
                bind(transportUseCase).to(TransportUseCase.class);
                bind(oxygenUseCase).to(OxygenUseCase.class);
                bind(artistRankingUseCase).to(ArtistRankingUseCase.class);
            }
        });
        return resourceConfig;
    }
}
