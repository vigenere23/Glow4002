package ca.ulaval.glo4002.booking.api.resources.oxygen.dto;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryEntry;

import java.util.ArrayList;
import java.util.List;

public class OxygenHistoryMapper {

    public List<OxygenHistoryDto> toDto(List<OxygenHistoryEntry> history) {
        List<OxygenHistoryDto> historyDto = new ArrayList<>();
        for (OxygenHistoryEntry historyEntry : history) {
            OxygenHistoryDto oxygenHistoryDto = new OxygenHistoryDto();
            oxygenHistoryDto.date = historyEntry.getDate();
            oxygenHistoryDto.qtyOxygenTankBought = historyEntry.getTankBought();
            oxygenHistoryDto.qtyWaterUsed = historyEntry.getWaterUsed();
            oxygenHistoryDto.qtyCandlesUsed = historyEntry.getCandlesUsed();
            oxygenHistoryDto.qtyOxygenTankMade = historyEntry.getTankMade();
            historyDto.add(oxygenHistoryDto);
        }
        return historyDto;
    }
}
