package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventoryEntry;

public interface OxygenSupplier {
    public void supply(LocalDate orderDate, int minQuantityToProduce, OxygenInventoryEntry inventoryEntry);
}
