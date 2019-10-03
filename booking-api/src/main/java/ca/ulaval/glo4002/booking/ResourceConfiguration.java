package ca.ulaval.glo4002.booking;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.persistance.heap.HeapRepository;

public class ResourceConfiguration extends ResourceConfig {

	ServiceLocator serviceLocator;

	public ResourceConfiguration(Glow4002 festival, Repository repository) {
		register(new AbstractBinder() {

			@Override
			protected void configure() {

				bind(repository).to(Repository.class);
				bind(festival).to(Glow4002.class);
			}
		});
	}

	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

}
