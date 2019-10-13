package ca.ulaval.glo4002.booking.domain.orders;

import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.orders.PassOrder;

public interface PassOrderRepository {
    public Optional<PassOrder> getById(Long id);
    public void save(PassOrder order);
}