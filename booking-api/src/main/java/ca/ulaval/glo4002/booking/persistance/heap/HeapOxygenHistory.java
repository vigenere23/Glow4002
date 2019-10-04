package ca.ulaval.glo4002.booking.persistance.heap;

import java.time.OffsetDateTime;
import java.util.HashMap;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.History;

public class HeapOxygenHistory implements OxygenHistory {

    private final HashMap<OffsetDateTime, History> history;

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
        if (history == null) return;
        this.history.put(date, history);
    }

    @Override
    public History getCreationHistoryPerDate(OffsetDateTime date) {
        History historyPerDate = null;
        if (history.containsKey(date) )
        {
            historyPerDate = history.get(date);
        } else {
            historyPerDate = new History();
            historyPerDate.date = date;
        }
        return historyPerDate;
    }
}