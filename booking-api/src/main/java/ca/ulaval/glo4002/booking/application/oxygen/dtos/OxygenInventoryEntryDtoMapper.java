package ca.ulaval.glo4002.booking.application.oxygen.dtos;

import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;

import java.util.List;
import java.util.stream.Collectors;

public class OxygenInventoryEntryDtoMapper {

    public List<OxygenInventoryEntryDto> toDtos(List<OxygenInventoryEntry> inventoryEntries) {
        return inventoryEntries
            .stream()
            .filter(entry -> entry.getTotalQuantity() > 0)
            .map(entry -> toDto(entry))
            .collect(Collectors.toList());
    }

    public OxygenInventoryEntryDto toDto(OxygenInventoryEntry inventoryEntry) {
        return new OxygenInventoryEntryDto(inventoryEntry.getOxygenGrade(), inventoryEntry.getTotalQuantity());
    }
}
