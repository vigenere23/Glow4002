package ca.ulaval.glo4002.booking.api.dtoMappers;

import ca.ulaval.glo4002.booking.api.dtos.oxygen.OxygenInventoryDto;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventory;

import java.util.ArrayList;
import java.util.List;

public class OxygenInventoryMapper {

    public List<OxygenInventoryDto> toDto(List<OxygenInventory> oxygenInventories) {
        ArrayList<OxygenInventoryDto> inventoryDto = new ArrayList<OxygenInventoryDto>();
        for (OxygenInventory oxygenInventory: oxygenInventories) {
            OxygenInventoryDto oxygenInventoryDto = new OxygenInventoryDto();
            oxygenInventoryDto.gradeTankOxygen = oxygenInventory.getOxygenGrade().toString();
            oxygenInventoryDto.quantity = oxygenInventory.getTotalQuantity();
            inventoryDto.add(oxygenInventoryDto);
        }
        return inventoryDto;
    }
}
