package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryItem;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;

public class HeapOxygenHistoryRepository implements OxygenHistoryRepository {

    private SortedMap<LocalDate, OxygenHistoryItem> history;

    public HeapOxygenHistoryRepository() {
        history = new TreeMap<>();
    }

    @Override
    public void save(OxygenHistoryItem historyItem) {
        history.put(historyItem.getDate(), historyItem);
    }

    @Override
    public OxygenHistoryItem findOxygenHistoryOfDate(LocalDate date) {
        return history.containsKey(date) ? history.get(date) : new OxygenHistoryItem(date);
    }

    @Override
    public SortedMap<LocalDate, OxygenHistoryItem> findAll() {
        return history;
    }
}