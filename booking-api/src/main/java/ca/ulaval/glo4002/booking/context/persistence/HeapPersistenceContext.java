package ca.ulaval.glo4002.booking.context.persistence;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapProfitRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapShuttleRepository;

public class HeapPersistenceContext extends AbstractBinder {

    @Override
    protected void configure() {
        bind(HeapPassOrderRepository.class).to(PassOrderRepository.class).in(Singleton.class);
        bind(HeapPassRepository.class).to(PassRepository.class).in(Singleton.class);
        bind(HeapShuttleRepository.class).to(ShuttleRepository.class).in(Singleton.class);
        bind(HeapOxygenHistoryRepository.class).to(OxygenHistoryRepository.class).in(Singleton.class);
        bind(HeapOxygenInventoryRepository.class).to(OxygenInventoryRepository.class).in(Singleton.class);
        bind(HeapProfitRepository.class).to(ProfitRepository.class).in(Singleton.class);
    }
}
