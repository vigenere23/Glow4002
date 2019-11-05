package ca.ulaval.glo4002.booking;

import ca.ulaval.glo4002.booking.domain.application.OxygenUseCase;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenProducer;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.domain.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.domain.transport.TransportExposer;

public class ResourceConfiguration extends ResourceConfig {

    public ResourceConfiguration(
        OxygenProducer oxygenProducer,
        TransportRequester transportRequester,
        PassOrderFactory passOrderFactory,
        PassOrderUseCase passOrderUseCase,
        OxygenUseCase oxygenUseCase,
        ArtistRankingUseCase artistRankingUseCase
    ) {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(oxygenProducer).to(OxygenProducer.class);
                bind(transportRequester).to(TransportExposer.class);
                bind(passOrderFactory).to(PassOrderFactory.class);
                bind(passOrderUseCase).to(PassOrderUseCase.class);
                bind(oxygenUseCase).to(OxygenUseCase.class);
                bind(artistRankingUseCase).to(ArtistRankingUseCase.class);
            }
        });
    }
}
