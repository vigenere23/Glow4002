package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Pass> findAttendingAtDate(LocalDate eventDate) {
        return passes
            .stream()
            .filter(pass -> pass.isAttendingAtDate(eventDate))
            .collect(Collectors.toList());
    }

    @Override
    public void save(Pass pass) {
        if (!passes.contains(pass) && pass != null) {
            passes.add(pass);
        }
    }
}
