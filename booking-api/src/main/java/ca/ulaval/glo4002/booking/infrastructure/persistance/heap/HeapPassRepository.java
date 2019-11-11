package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;

public class HeapPassRepository implements PassRepository {

    private List<Pass> passes;

    public HeapPassRepository() {
        passes = new ArrayList<>();
    }

    @Override
    public List<Pass> findAll() {
        return passes;
    }

    @Override
    public void save(Pass pass) {
        passes.add(pass);
    }
}
