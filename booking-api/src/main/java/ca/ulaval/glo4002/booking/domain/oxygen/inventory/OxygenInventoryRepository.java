package ca.ulaval.glo4002.booking.domain.oxygen.inventory;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public interface OxygenInventoryRepository {
    public OxygenInventoryEntry find(OxygenGrade oxygenGrade);
    public List<OxygenInventoryEntry> findAll();
    public void save(OxygenInventoryEntry entry);
}
