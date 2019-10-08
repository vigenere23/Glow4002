package ca.ulaval.glo4002.booking;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.services.exposers.OxygenExposer;
import ca.ulaval.glo4002.booking.services.exposers.PassOrderExposer;
import ca.ulaval.glo4002.booking.services.exposers.TransportExposer;
import ca.ulaval.glo4002.booking.services.orchestrators.PassOrderingOrchestrator;

public class ResourceConfiguration extends ResourceConfig {

    public ResourceConfiguration(Repository repository, OxygenExposer oxygenExposer, TransportExposer transportExposer, PassOrderExposer passOrderExposer, PassOrderingOrchestrator orchestrator) {
        register(new AbstractBinder() {

            @Override
            protected void configure() {
                bind(oxygenExposer).to(OxygenExposer.class);
                bind(repository).to(Repository.class);
                bind(transportExposer).to(TransportExposer.class);
                bind(passOrderExposer).to(PassOrderExposer.class);
                bind(orchestrator).to(PassOrderingOrchestrator.class);
            }
        });
    }
}
