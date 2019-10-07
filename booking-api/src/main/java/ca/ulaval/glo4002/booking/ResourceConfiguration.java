package ca.ulaval.glo4002.booking;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.services.orchestrators.PassOrderingOrchestrator;
import ca.ulaval.glo4002.booking.services.orders.PassOrderService;
import ca.ulaval.glo4002.booking.services.oxygen.OxygenExposer;
import ca.ulaval.glo4002.booking.services.transport.TransportExposer;

public class ResourceConfiguration extends ResourceConfig {

    public ResourceConfiguration(Repository repository, OxygenExposer oxygen, TransportExposer transportExposer, PassOrderService passOrderService, PassOrderingOrchestrator orchestrator) {
        register(new AbstractBinder() {

            @Override
            protected void configure() {
                bind(oxygen).to(OxygenExposer.class);
                bind(repository).to(Repository.class);
                bind(transportExposer).to(TransportExposer.class);
                bind(passOrderService).to(PassOrderService.class);
                bind(orchestrator).to(PassOrderingOrchestrator.class);
            }
        });
    }
}
