package ca.ulaval.glo4002.booking.context.application.profit;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.profit.ProfitSaver;

public class ProfitContext extends AbstractBinder {

    @Override
    protected void configure() {
        bind(ProfitSaver.class).to(IncomeSaver.class);
        bind(ProfitSaver.class).to(OutcomeSaver.class);
    }
}
