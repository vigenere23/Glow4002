package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;
import java.util.List;

public interface PassRepository {
    
    public List<Pass> findAll();
    public List<Pass> findAttendingAtDate(LocalDate eventDate);
    public void save(Pass pass);
}
