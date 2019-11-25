package ca.ulaval.glo4002.booking.context.application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.context.application.dates.DatesContext;

public class ApplicationContext extends AbstractBinder {

    @Override
    protected void configure() {
        install(new DatesContext());
    }
}
