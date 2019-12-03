package ca.ulaval.glo4002.booking.domain.oxygen.inventory;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public interface OxygenInventoryRepository {
    OxygenInventoryEntry find(OxygenGrade oxygenGrade);
    List<OxygenInventoryEntry> findAll();
    void replace(OxygenInventoryEntry entry);
}
