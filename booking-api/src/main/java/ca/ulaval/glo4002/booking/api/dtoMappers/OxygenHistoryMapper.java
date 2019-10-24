package ca.ulaval.glo4002.booking.api.dtoMappers;

import ca.ulaval.glo4002.booking.api.dtos.oxygen.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.domain.oxygen.HistoryType;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class OxygenHistoryMapper {

    public List<OxygenHistoryDto> toDto(SortedMap<LocalDate, OxygenHistory> history) {
        ArrayList<OxygenHistoryDto> historyDto = new ArrayList<OxygenHistoryDto>();
        for (OxygenHistory oxygenHistory: history.values()) {
            OxygenHistoryDto oxygenHistoryDto = new OxygenHistoryDto();
            oxygenHistoryDto.date = oxygenHistory.getDate();
            oxygenHistoryDto.qtyOxygenTankBought = oxygenHistory.getOxygenHistory().get(HistoryType.OXYGEN_TANK_BOUGHT);
        }
        return historyDto;
    }
}
