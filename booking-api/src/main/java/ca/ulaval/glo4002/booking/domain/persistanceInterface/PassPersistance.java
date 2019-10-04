package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.ID;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;

public interface PassPersistance {
    public Optional<Pass> getById(ID id);
    public void save(Pass pass);
}
