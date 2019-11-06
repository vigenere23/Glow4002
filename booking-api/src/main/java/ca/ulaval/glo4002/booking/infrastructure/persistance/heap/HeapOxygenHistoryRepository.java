package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenDateHistory;

public class HeapOxygenHistoryRepository implements OxygenHistoryRepository {

    private SortedMap<LocalDate, OxygenDateHistory> history;

    public HeapOxygenHistoryRepository() {
        history = new TreeMap<>();
    }

    @Override
    public void saveOxygenHistory(SortedMap<LocalDate, OxygenDateHistory> history) {
        this.history = history;
    }

    @Override
    public SortedMap<LocalDate, OxygenDateHistory> findOxygenHistory() {
        return history;
    }
}