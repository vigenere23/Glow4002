package ca.ulaval.glo4002.booking.api.resources.oxygen.dto;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventory;

import java.util.ArrayList;
import java.util.List;

public class OxygenInventoryMapper {

    public List<OxygenInventoryDto> toDto(List<OxygenInventory> oxygenInventories) {
        ArrayList<OxygenInventoryDto> inventoryDto = new ArrayList<>();
        for (OxygenInventory oxygenInventory: oxygenInventories) {
            OxygenInventoryDto oxygenInventoryDto = new OxygenInventoryDto();
            oxygenInventoryDto.gradeTankOxygen = oxygenInventory.getOxygenGrade().toString();
            oxygenInventoryDto.quantity = oxygenInventory.getInventory();
            inventoryDto.add(oxygenInventoryDto);
        }
        return inventoryDto;
    }
}
