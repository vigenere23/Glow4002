package ca.ulaval.glo4002.booking;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

import ca.ulaval.glo4002.booking.persistance.inMemory.InMemoryRepository;
import ca.ulaval.glo4002.booking.persistance.intefcace.Repository;

public class RessourceConfiguration extends ResourceConfig {

	// won't be initialized until onStartup()
	ServiceLocator serviceLocator;

	public RessourceConfiguration() {
		register(new AbstractBinder() {

			@Override
			protected void configure() {
				bind(InMemoryRepository.class).to(Repository.class);
			}
		});
	}

	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

}
