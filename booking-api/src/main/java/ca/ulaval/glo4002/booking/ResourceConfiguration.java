package ca.ulaval.glo4002.booking;

import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.transport.TransportExposer;

public class ResourceConfiguration extends ResourceConfig {

    public ResourceConfiguration(
        OxygenRequester oxygenRequester,
        TransportRequester transportRequester,
        PassOrderFactory passOrderFactory,
        PassOrderUseCase passOrderUseCase
    ) {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(oxygenRequester).to(OxygenRequester.class);
                bind(transportRequester).to(TransportExposer.class);
                bind(passOrderFactory).to(PassOrderFactory.class);
                bind(passOrderUseCase).to(PassOrderUseCase.class);
            }
        });
    }
}
