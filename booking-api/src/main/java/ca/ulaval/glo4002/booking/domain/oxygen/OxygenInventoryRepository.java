package ca.ulaval.glo4002.booking.domain.oxygen;

import java.util.EnumMap;

public interface OxygenInventoryRepository {
    
    public void saveOxygenInventories(EnumMap<OxygenGrade, OxygenInventory> oxygenInventories);
    public EnumMap<OxygenGrade, OxygenInventory> findInventories();
}
