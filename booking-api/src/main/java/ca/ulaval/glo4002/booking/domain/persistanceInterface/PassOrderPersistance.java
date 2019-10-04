package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;

public interface PassOrderPersistance {
    public Optional<PassOrder> getById(Long id);
    public void save(PassOrder order);
}
