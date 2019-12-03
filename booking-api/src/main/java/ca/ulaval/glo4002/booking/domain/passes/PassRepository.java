package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;
import java.util.List;

public interface PassRepository {
    List<Pass> findAll();
    List<Pass> findAttendingAtDate(LocalDate eventDate);
    void add(Pass pass);
}
