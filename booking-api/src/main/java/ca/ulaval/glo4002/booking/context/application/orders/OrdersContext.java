package ca.ulaval.glo4002.booking.context.application.orders;

import java.util.concurrent.atomic.AtomicLong;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.orders.PassOrderUseCase;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassDtoMapper;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDtoMapper;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscountLinker;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumberFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassPriceFactory;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumberFactory;

public class OrdersContext extends AbstractBinder {

    @Override
    protected void configure() {
        PassNumberFactory passNumberFactory = new PassNumberFactory(new AtomicLong(0));
        bind(passNumberFactory).to(PassNumberFactory.class);
        bindAsContract(PassPriceFactory.class);
        bindAsContract(PassFactory.class);
        bindAsContract(PassDtoMapper.class);
        
        OrderNumberFactory orderNumberFactory = new OrderNumberFactory(new AtomicLong(0));
        bind(orderNumberFactory).to(OrderNumberFactory.class);
        bindAsContract(OrderDiscountLinker.class);
        bindAsContract(PassOrderFactory.class);
        bindAsContract(PassOrderDtoMapper.class);
        bindAsContract(PassOrderUseCase.class);
    }
}
