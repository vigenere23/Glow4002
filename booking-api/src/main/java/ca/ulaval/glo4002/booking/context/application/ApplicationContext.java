package ca.ulaval.glo4002.booking.context.application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.context.application.artists.ArtistsContext;
import ca.ulaval.glo4002.booking.context.application.dates.DatesContext;
import ca.ulaval.glo4002.booking.context.application.orders.OrdersContext;
import ca.ulaval.glo4002.booking.context.application.oxygen.OxygenContext;
import ca.ulaval.glo4002.booking.context.application.profit.ProfitContext;
import ca.ulaval.glo4002.booking.context.application.program.ProgramContext;
import ca.ulaval.glo4002.booking.context.application.transport.TransportContext;

public class ApplicationContext extends AbstractBinder {

    @Override
    protected void configure() {
        DatesContext datesContext = new DatesContext();
        
        install(datesContext);
        install(new ProfitContext());
        install(new TransportContext());
        install(new OxygenContext());
        install(new OrdersContext());
        install(new ArtistsContext());
        install(new ProgramContext(datesContext.getGlow4002Dates()));
    }
}
