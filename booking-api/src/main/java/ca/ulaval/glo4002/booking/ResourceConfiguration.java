package ca.ulaval.glo4002.booking;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.domain.orchestrators.PassOrderingOrchestrator;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderExposer;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenExposer;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportExposer;

public class ResourceConfiguration extends ResourceConfig {

    public ResourceConfiguration(
        PassOrderRepository passOrderRepository,
        PassRepository passRepository,
        OxygenHistoryRepository oxygenHistoryRepository,
        OxygenInventoryRepository oxygenInventoryRepository,
        ShuttleRepository shuttleRepository,
        OxygenExposer oxygenExposer,
        TransportExposer transportExposer,
        PassOrderExposer passOrderExposer,
        PassOrderingOrchestrator orchestrator
    ) {
        register(new AbstractBinder() {

            @Override
            protected void configure() {
                bind(passOrderRepository).to(PassOrderRepository.class);
                bind(passRepository).to(PassRepository.class);
                bind(oxygenHistoryRepository).to(OxygenHistoryRepository.class);
                bind(oxygenInventoryRepository).to(OxygenInventoryRepository.class);
                bind(shuttleRepository).to(ShuttleRepository.class);
                bind(oxygenExposer).to(OxygenExposer.class);
                bind(transportExposer).to(TransportExposer.class);
                bind(passOrderExposer).to(PassOrderExposer.class);
                bind(orchestrator).to(PassOrderingOrchestrator.class);
            }
        });
    }
}
