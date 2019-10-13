package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import ca.ulaval.glo4002.booking.domain.orders.ID;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;

public class HeapPassRepository implements PassRepository {

    private static final AtomicLong idGenerator = new AtomicLong(0);
    private Map<ID, Pass> passes;

    public HeapPassRepository() {
        passes = new HashMap<>();
    }

    @Override
    public Optional<Pass> getById(ID id) {
        Pass pass = passes.get(id);
        return Optional.ofNullable(pass);
    }

    @Override
    public void save(Pass pass) {
        if (!passes.containsValue(pass) && pass != null) {            
            pass.setPassNumber(new ID(idGenerator.getAndIncrement()));
            passes.put(pass.getPassNumber(), pass);
        }
    }
}
