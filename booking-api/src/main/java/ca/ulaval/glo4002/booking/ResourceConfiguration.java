package ca.ulaval.glo4002.booking;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.domain.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.orchestrators.PassOrderingOrchestrator;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderExposer;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenExposer;
import ca.ulaval.glo4002.booking.domain.transport.TransportExposer;

public class ResourceConfiguration extends ResourceConfig {

    public ResourceConfiguration(
        OxygenExposer oxygenExposer,
        TransportExposer transportExposer,
        PassOrderExposer passOrderExposer,
        PassOrderingOrchestrator orchestrator,
        ArtistRankingUseCase artistRankingUseCase
    ) {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(oxygenExposer).to(OxygenExposer.class);
                bind(transportExposer).to(TransportExposer.class);
                bind(passOrderExposer).to(PassOrderExposer.class);
                bind(orchestrator).to(PassOrderingOrchestrator.class);
                bind(artistRankingUseCase).to(ArtistRankingUseCase.class);
            }
        });
    }
}
