package ca.ulaval.glo4002.booking.api.dtoMappers;

import ca.ulaval.glo4002.booking.api.dtos.oxygen.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.domain.oxygen.HistoryType;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenDateHistory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.SortedMap;

public class OxygenHistoryMapper {

    public List<OxygenHistoryDto> toDto(SortedMap<LocalDate, OxygenDateHistory> history) {
        ArrayList<OxygenHistoryDto> historyDto = new ArrayList<>();
        for (OxygenDateHistory oxygenDateHistory : history.values()) {
            OxygenHistoryDto oxygenHistoryDto = new OxygenHistoryDto();
            oxygenHistoryDto.date = oxygenDateHistory.getDate();
            EnumMap<HistoryType, Integer> oxygenHistory = oxygenDateHistory.getOxygenHistory();
            oxygenHistoryDto.qtyOxygenTankBought = oxygenHistory.get(HistoryType.OXYGEN_TANK_BOUGHT);
            oxygenHistoryDto.qtyWaterUsed = oxygenHistory.get(HistoryType.WATER_USED);
            oxygenHistoryDto.qtyCandlesUsed = oxygenHistory.get(HistoryType.CANDLES_USED);
            oxygenHistoryDto.qtyOxygenTankMade = oxygenHistory.get(HistoryType.OXYGEN_TANK_MADE);
        }
        return historyDto;
    }
}
