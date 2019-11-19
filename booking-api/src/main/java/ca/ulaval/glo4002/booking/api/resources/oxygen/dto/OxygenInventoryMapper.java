package ca.ulaval.glo4002.booking.api.resources.oxygen.dto;

import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventoryEntry;

import java.util.ArrayList;
import java.util.List;

public class OxygenInventoryMapper {

    public List<OxygenInventoryDto> toDto(List<OxygenInventoryEntry> oxygenInventory) {
        List<OxygenInventoryDto> inventoryDto = new ArrayList<>();
        for (OxygenInventoryEntry oxygenInventoryEntry : oxygenInventory) {
            int quantity = oxygenInventoryEntry.getTotalQuantity();
            if (quantity > 0) {
                OxygenInventoryDto oxygenInventoryDto = new OxygenInventoryDto();
                oxygenInventoryDto.gradeTankOxygen = oxygenInventoryEntry.getOxygenGrade().toString();
                oxygenInventoryDto.quantity = quantity;
                inventoryDto.add(oxygenInventoryDto);
            }
        }
        return inventoryDto;
    }
}
