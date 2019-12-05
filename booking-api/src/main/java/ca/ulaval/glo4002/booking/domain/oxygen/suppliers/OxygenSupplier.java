package ca.ulaval.glo4002.booking.domain.oxygen.suppliers;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;

public interface OxygenSupplier {
    void supply(OffsetDateTime orderDate, int minQuantityToProduce, OxygenInventoryEntry inventoryEntry);
}
