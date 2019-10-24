package ca.ulaval.glo4002.booking.api.dtoMappers;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.api.dtos.oxygen.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.api.dtos.oxygen.OxygenInventoryDto;

public class OxygenMapper {

    public List<OxygenInventoryDto> convertInventoryToDto(List<Inventory> inventory) {
        ArrayList<OxygenInventoryDto> inventoryDto = new ArrayList<OxygenInventoryDto>();
        for (Inventory inventoryItem : inventory) {
            OxygenInventoryDto inventoryDtoItem = new OxygenInventoryDto();
            inventoryDtoItem.gradeTankOxygen = inventoryItem.gradeTankOxygen;
            inventoryDtoItem.quantity = inventoryItem.quantity;
            inventoryDto.add(inventoryDtoItem);
        }
        return inventoryDto;
    }

    public List<OxygenHistoryDto> convertHistoryToDto(List<History> history) {
        ArrayList<OxygenHistoryDto> historyDto = new ArrayList<OxygenHistoryDto>();
        for (History historyItem : history) {
            OxygenHistoryDto historyDtoItem = new OxygenHistoryDto();
            historyDtoItem.date = historyItem.date;
            historyDtoItem.qtyCandlesUsed = historyItem.qtyCandlesUsed;
            historyDtoItem.qtyOxygenTankBought = historyItem.qtyOxygenTankBought;
            historyDtoItem.qtyOxygenTankMade = historyItem.qtyOxygenTankMade;
            historyDtoItem.qtyWaterUsed = historyItem.qtyWaterUsed;
            historyDto.add(historyDtoItem);
        }
        return historyDto;
    }

}