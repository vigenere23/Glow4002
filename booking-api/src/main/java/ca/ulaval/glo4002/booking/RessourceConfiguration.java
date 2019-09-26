package ca.ulaval.glo4002.booking;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;


import ca.ulaval.glo4002.booking.persistance.inMemory.InMemoryRepository;
import ca.ulaval.glo4002.booking.domain.persistanceInteface.Repository;

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
