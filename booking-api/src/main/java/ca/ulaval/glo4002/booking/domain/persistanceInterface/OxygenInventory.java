package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.Inventory;

public interface OxygenInventory {
    
    public void setOxygenInventory(OxygenGrade grade, int quantity);
    public int getInventoryOfGrade(OxygenGrade grade);
    public List<Inventory> getCompleteInventory();
    public void setOxygenRemaining(OxygenGrade grade, int quantity);
    public int getOxygenRemaining(OxygenGrade grade);
}
