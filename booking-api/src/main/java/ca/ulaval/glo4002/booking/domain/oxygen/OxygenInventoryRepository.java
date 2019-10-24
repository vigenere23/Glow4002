package ca.ulaval.glo4002.booking.domain.oxygen;

import java.util.List;

public interface OxygenInventoryRepository {
    
    public void saveOxygenInventory(OxygenGrade grade, int quantity);
    public int findInventoryOfGrade(OxygenGrade grade);
    public List<Inventory> findCompleteInventory();
    public void saveOxygenRemaining(OxygenGrade grade, int quantity);
    public int findOxygenRemaining(OxygenGrade grade);
}
