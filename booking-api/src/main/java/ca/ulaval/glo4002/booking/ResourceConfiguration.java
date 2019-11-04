package ca.ulaval.glo4002.booking;

import ca.ulaval.glo4002.booking.application.TransportUseCase;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.domain.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderExposer;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenExposer;
import ca.ulaval.glo4002.booking.domain.transport.TransportExposer;

public class ResourceConfiguration extends ResourceConfig {

    public ResourceConfiguration(
        OxygenRequester oxygenRequester,
        TransportUseCase transportUseCase,
        PassOrderFactory passOrderFactory,
        PassOrderUseCase passOrderUseCase,
        ArtistRankingUseCase artistRankingUseCase
    ) {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(oxygenRequester).to(OxygenRequester.class);
                bind(transportUseCase).to(TransportUseCase.class);
                bind(passOrderFactory).to(PassOrderFactory.class);
                bind(passOrderUseCase).to(PassOrderUseCase.class);
                bind(artistRankingUseCase).to(ArtistRankingUseCase.class);
            }
        });
    }
}
