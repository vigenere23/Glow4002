package ca.ulaval.glo4002.booking.context.application.orders;

import java.util.concurrent.atomic.AtomicLong;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.orders.PassOrderUseCase;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDtoMapper;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumberFactory;

public class PassOrderContext extends AbstractBinder {

    @Override
    protected void configure() {
        OrderNumberFactory orderNumberFactory = new OrderNumberFactory(new AtomicLong(0));
        bind(orderNumberFactory).to(OrderNumberFactory.class);
        bindAsContract(PassOrderDtoMapper.class);
        bindAsContract(PassOrderUseCase.class);
    }
}
