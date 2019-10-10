package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import java.time.LocalDate;
import java.util.HashMap;

import ca.ulaval.glo4002.booking.domain.oxygen.History;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;

public class HeapOxygenHistoryRepository implements OxygenHistoryRepository {

    private final HashMap<LocalDate, History> history;

    public HeapOxygenHistoryRepository() {
        super();
        history = new HashMap<LocalDate, History>();
    }

    @Override
    public HashMap<LocalDate, History> getCreationHistory() {
        return history;
    }

    @Override
    public void updateCreationHistory(LocalDate date, History history) {
        if (history == null) return;
        this.history.put(date, history);
    }

    @Override
    public History getCreationHistoryPerDate(LocalDate date) {
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