package ca.ulaval.glo4002.booking.domain.oxygen;

import java.util.EnumMap;

public interface OxygenInventoryRepository {
    
    public EnumMap<OxygenGrade, OxygenInventory> findAll();
    public OxygenInventory findByGrade(OxygenGrade oxygenGrade);
    public void save(OxygenInventory oxygenInventory);
}
