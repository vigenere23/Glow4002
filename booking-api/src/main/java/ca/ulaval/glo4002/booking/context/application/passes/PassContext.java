package ca.ulaval.glo4002.booking.context.application.passes;

import java.util.concurrent.atomic.AtomicLong;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.orders.dtos.PassDtoMapper;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassPriceFactory;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumberFactory;

public class PassContext extends AbstractBinder {

    @Override
    protected void configure() {
        PassNumberFactory passNumberFactory = new PassNumberFactory(new AtomicLong(0));
        bind(passNumberFactory).to(PassNumberFactory.class);
        bindAsContract(PassPriceFactory.class);
        bindAsContract(PassFactory.class);
        bindAsContract(PassDtoMapper.class);
    }
}
