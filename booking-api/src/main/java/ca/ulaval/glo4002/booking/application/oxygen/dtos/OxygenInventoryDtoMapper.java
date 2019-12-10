package ca.ulaval.glo4002.booking.application.oxygen.dtos;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;

public class OxygenInventoryDtoMapper {

    public OxygenInventoryDto toDto(List<OxygenInventoryEntry> inventoryEntries) {
        return new OxygenInventoryDto(
            inventoryEntries
                .stream()
                .filter(entry -> entry.getTotalQuantity() > 0)
                .map(entry -> toEntryDto(entry))
                .collect(Collectors.toList())
        );
    }

    private OxygenInventoryEntryDto toEntryDto(OxygenInventoryEntry inventoryEntry) {
        return new OxygenInventoryEntryDto(inventoryEntry.getOxygenGrade(), inventoryEntry.getTotalQuantity());
    }
}
