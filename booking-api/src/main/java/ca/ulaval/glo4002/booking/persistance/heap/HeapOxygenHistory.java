package ca.ulaval.glo4002.booking.persistance.heap;

import java.time.OffsetDateTime;
import java.util.HashMap;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.HistoryDto;

public class HeapOxygenHistory implements OxygenHistory {

    private HashMap<OffsetDateTime, HistoryDto> history;

    public HeapOxygenHistory() {
        super();
        history = new HashMap<OffsetDateTime, HistoryDto>();
    }

    @Override
    public HashMap<OffsetDateTime, HistoryDto> getCreationHistory() {
        return history;
    }

    @Override
    public void updateCreationHistory(OffsetDateTime date, HistoryDto history) {
        this.history.put(date, history);
    }

    @Override
    public HistoryDto getCreationHistoryPerDate(OffsetDateTime date) {
        if(history.containsKey(date) )
        {
            return history.get(date);
        } else {
            HistoryDto newHistory = new HistoryDto();
            newHistory.date = date;
            return newHistory;
        }
    }
}