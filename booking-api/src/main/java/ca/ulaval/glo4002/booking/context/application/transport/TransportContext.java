package ca.ulaval.glo4002.booking.context.application.transport;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.transport.TransportUseCase;
import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDtoMapper;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleFactory;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleFiller;

public class TransportContext extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(ShuttleFiller.class);
        bindAsContract(TransportReserver.class);
        bindAsContract(ShuttleFactory.class);
        bindAsContract(ShuttleDtoMapper.class);
        bindAsContract(TransportUseCase.class);
    }
}
