package ca.ulaval.glo4002.booking.context;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.context.application.ApplicationContext;
import ca.ulaval.glo4002.booking.context.external_apis.ExternalApisContext;
import ca.ulaval.glo4002.booking.context.persistence.InMemoryPersistenceContext;

public class BookingContext extends AbstractBinder {

	@Override
	protected void configure() {
		install(new ApplicationContext());
		install(new InMemoryPersistenceContext());
		install(new ExternalApisContext());
	}
}
