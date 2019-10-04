package ca.ulaval.glo4002.booking;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.domain.Orchester;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrderService;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.domain.transport.TransportExposer;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenExposer;

public class ResourceConfiguration extends ResourceConfig {

	public ResourceConfiguration(Repository repository, OxygenExposer oxygen, TransportExposer transportExposer, PassOrderService passOrderService, Orchester orchester) {
		register(new AbstractBinder() {

			@Override
			protected void configure() {
				bind(oxygen).to(OxygenExposer.class);
                bind(repository).to(Repository.class);
                bind(transportExposer).to(TransportExposer.class);
				bind(passOrderService).to(PassOrderService.class);
				bind(orchester).to(Orchester.class);
			}
		});
	}
}
