package ca.ulaval.glo4002.booking.domain.passes;

import java.util.List;

public interface PassRepository {
    
    public List<Pass> findAll();
    public void save(Pass pass);
}
