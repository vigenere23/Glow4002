package ca.ulaval.glo4002.booking.context.application.profit;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.profit.ProfitUseCase;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.profit.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.profit.ProfitSaver;

public class ProfitContext extends AbstractBinder {

    @Override
    protected void configure() {
        bind(ProfitSaver.class).to(IncomeSaver.class).in(Singleton.class);
        bind(ProfitSaver.class).to(OutcomeSaver.class).in(Singleton.class);
        bindAsContract(ProfitCalculator.class);
        bindAsContract(ProfitUseCase.class);
    }
}
