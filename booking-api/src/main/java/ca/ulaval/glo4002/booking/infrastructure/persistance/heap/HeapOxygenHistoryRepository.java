package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistory;

public class HeapOxygenHistoryRepository implements OxygenHistoryRepository {

    private SortedMap<LocalDate, OxygenHistory> history;

    public HeapOxygenHistoryRepository() {
        super();
        history = new TreeMap<LocalDate, OxygenHistory>();
    }

    @Override
    public SortedMap<LocalDate, OxygenHistory> findOxygenHistory() {
        return history;
    }

    @Override
    public void saveOxygenHistory(SortedMap<LocalDate, OxygenHistory> history) {
        this.history = history;
    }
}