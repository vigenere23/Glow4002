package ca.ulaval.glo4002.booking.api.dtoMappers;

import ca.ulaval.glo4002.booking.api.dtos.oxygen.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenDateHistory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class OxygenHistoryMapper {

    public List<OxygenHistoryDto> toDto(SortedMap<LocalDate, OxygenDateHistory> history) {
        List<OxygenHistoryDto> historyDto = new ArrayList<>();
        for (OxygenDateHistory oxygenDateHistory : history.values()) {
            OxygenHistoryDto oxygenHistoryDto = new OxygenHistoryDto();
            oxygenHistoryDto.date = oxygenDateHistory.getDate();
            oxygenHistoryDto.qtyOxygenTankBought = oxygenDateHistory.getOxygenTankBought();
            oxygenHistoryDto.qtyWaterUsed = oxygenDateHistory.getWaterUsed();
            oxygenHistoryDto.qtyCandlesUsed = oxygenDateHistory.getCandlesUsed();
            oxygenHistoryDto.qtyOxygenTankMade = oxygenDateHistory.getOxygenTankMade();
            historyDto.add(oxygenHistoryDto);
        }
        return historyDto;
    }
}
