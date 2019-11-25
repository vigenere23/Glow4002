package ca.ulaval.glo4002.booking.context.application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.context.application.dates.DatesContext;
import ca.ulaval.glo4002.booking.context.application.orders.PassOrderContext;
import ca.ulaval.glo4002.booking.context.application.passes.PassContext;
import ca.ulaval.glo4002.booking.context.application.profit.ProfitContext;
import ca.ulaval.glo4002.booking.context.application.transport.TransportContext;

public class ApplicationContext extends AbstractBinder {

    @Override
    protected void configure() {
        install(new DatesContext());
        install(new ProfitContext());
        install(new TransportContext());
        install(new PassContext());
        install(new PassOrderContext());
    }
}
