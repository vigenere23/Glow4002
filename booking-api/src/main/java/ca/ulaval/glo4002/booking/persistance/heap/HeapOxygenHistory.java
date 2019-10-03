package ca.ulaval.glo4002.booking.persistance.heap;

import java.time.OffsetDateTime;
import java.util.HashMap;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.History;

public class HeapOxygenHistory implements OxygenHistory {

    private HashMap<OffsetDateTime, History> history;

    public HeapOxygenHistory() {
        super();
        history = new HashMap<OffsetDateTime, History>();
    }

    @Override
    public HashMap<OffsetDateTime, History> getCreationHistory() {
        return history;
    }

    @Override
    public void updateCreationHistory(OffsetDateTime date, History history) {
        this.history.put(date, history);
    }

    @Override
    public History getCreationHistoryPerDate(OffsetDateTime date) {
        if(history.containsKey(date) )
        {
            return history.get(date);
        } else {
            History newHistory = new History();
            newHistory.date = date;
            return newHistory;
        }
    }
}