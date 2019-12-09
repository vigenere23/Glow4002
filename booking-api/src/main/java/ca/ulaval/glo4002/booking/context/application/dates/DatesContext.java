package ca.ulaval.glo4002.booking.context.application.dates;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.dates.DatesUseCase;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.dates.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;

public class DatesContext extends AbstractBinder {

    private Glow4002Dates glow4002Dates;

    public DatesContext() {
        glow4002Dates = new Glow4002Dates();
    }

    @Override
    protected void configure() {
        bind(glow4002Dates).to(FestivalDates.class);
        bind(glow4002Dates).to(OxygenDates.class);
        bindAsContract(DatesUseCase.class);
    }

    public Glow4002Dates getGlow4002Dates() {
        return glow4002Dates;
    }
}
