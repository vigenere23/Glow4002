package ca.ulaval.glo4002.booking.domain.oxygen;

public interface OxygenInventoryRepository {
    
    public void saveOxygenInventory(OxygenInventory oxygenInventory);
    public OxygenInventory findInventoryOfGrade(OxygenGrade grade);
}
