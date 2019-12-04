package ca.ulaval.glo4002.booking.context.application.profit;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.profit.ProfitUseCase;
import ca.ulaval.glo4002.booking.domain.profit.ProfitCalculator;

public class ProfitContext extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(ProfitCalculator.class);
        bindAsContract(ProfitUseCase.class);
    }
}
