package ca.ulaval.glo4002.booking.context.application.dates;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.dates.DatesUseCase;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.dates.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;

public class DatesContext extends AbstractBinder {

    @Override
    protected void configure() {
        Glow4002Dates glow4002Dates = new Glow4002Dates();
        bind(glow4002Dates).to(FestivalDates.class);
        bind(glow4002Dates).to(OxygenDates.class);
        bindAsContract(DatesUseCase.class);
    }
}
