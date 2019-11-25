package ca.ulaval.glo4002.booking.context.application.dates;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.dates.DatesUseCase;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.dates.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;

public class DatesContext extends AbstractBinder {

    @Override
    protected void configure() {
        bind(Glow4002Dates.class).to(FestivalDates.class).in(Singleton.class);
        bind(Glow4002Dates.class).to(OxygenDates.class).in(Singleton.class);
        bindAsContract(DatesUseCase.class);
    }
}
