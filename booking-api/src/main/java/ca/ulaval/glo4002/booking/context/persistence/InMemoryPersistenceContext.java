package ca.ulaval.glo4002.booking.context.persistence;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistence.memory.InMemoryOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistence.memory.InMemoryOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistence.memory.InMemoryPassOrderRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistence.memory.InMemoryPassRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistence.memory.InMemoryProfitRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistence.memory.InMemoryShuttleRepository;

public class InMemoryPersistenceContext extends AbstractBinder {

    @Override
    protected void configure() {
        bind(InMemoryPassOrderRepository.class).to(PassOrderRepository.class).in(Singleton.class);
        bind(InMemoryPassRepository.class).to(PassRepository.class).in(Singleton.class);
        bind(InMemoryShuttleRepository.class).to(ShuttleRepository.class).in(Singleton.class);
        bind(InMemoryOxygenHistoryRepository.class).to(OxygenHistoryRepository.class).in(Singleton.class);
        bind(InMemoryOxygenInventoryRepository.class).to(OxygenInventoryRepository.class).in(Singleton.class);
        bind(InMemoryProfitRepository.class)
            .to(ProfitRepository.class)
            .to(IncomeSaver.class)
            .to(OutcomeSaver.class)
            .in(Singleton.class);
    }
}
