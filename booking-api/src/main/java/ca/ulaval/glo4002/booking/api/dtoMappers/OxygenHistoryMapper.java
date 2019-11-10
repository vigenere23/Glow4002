package ca.ulaval.glo4002.booking.api.dtoMappers;

import ca.ulaval.glo4002.booking.api.dtos.oxygen.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class OxygenHistoryMapper {

    public List<OxygenHistoryDto> toDto(SortedMap<LocalDate, OxygenHistoryItem> history) {
        List<OxygenHistoryDto> historyDto = new ArrayList<>();
        for (OxygenHistoryItem oxygenHistoryItem : history.values()) {
            OxygenHistoryDto oxygenHistoryDto = new OxygenHistoryDto();
            oxygenHistoryDto.date = oxygenHistoryItem.getDate();
            oxygenHistoryDto.qtyOxygenTankBought = oxygenHistoryItem.getOxygenTankBought();
            oxygenHistoryDto.qtyWaterUsed = oxygenHistoryItem.getWaterUsed();
            oxygenHistoryDto.qtyCandlesUsed = oxygenHistoryItem.getCandlesUsed();
            oxygenHistoryDto.qtyOxygenTankMade = oxygenHistoryItem.getOxygenTankMade();
            historyDto.add(oxygenHistoryDto);
        }
        return historyDto;
    }
}
