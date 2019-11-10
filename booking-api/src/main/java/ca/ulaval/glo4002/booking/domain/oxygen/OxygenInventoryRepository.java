package ca.ulaval.glo4002.booking.domain.oxygen;

import java.util.List;

public interface OxygenInventoryRepository {
    
    public List<OxygenInventory> findAll();
    public OxygenInventory findByGrade(OxygenGrade oxygenGrade);
    public void save(OxygenInventory oxygenInventory);
}
