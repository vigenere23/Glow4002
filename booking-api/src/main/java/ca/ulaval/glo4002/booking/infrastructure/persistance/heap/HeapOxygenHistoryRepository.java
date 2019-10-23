package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import ca.ulaval.glo4002.booking.domain.oxygen.History;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenProductionInventory;

public class HeapOxygenHistoryRepository implements OxygenHistoryRepository {

    private SortedMap<LocalDate, OxygenProductionInventory> history;

    public HeapOxygenHistoryRepository() {
        super();
        history = new TreeMap<LocalDate, OxygenProductionInventory>();
    }

    @Override
    public SortedMap<LocalDate, OxygenProductionInventory> findOxygenHistory() {
        return history;
    }

    @Override
    public void saveOxygenHistory(SortedMap<LocalDate, OxygenProductionInventory> history) {
        this.history = history;
    }

    @Override
    public void saveCreationHistory(LocalDate date, OxygenProductionInventory history) {
        if (history == null) return;
        this.history.put(date, history);
    }

    @Override
    public OxygenProductionInventory findCreationHistoryPerDate(LocalDate date) {
        OxygenProductionInventory historyPerDate = null;
        if (history.containsKey(date) )
        {
            historyPerDate = history.get(date);
        } else {
            historyPerDate = new OxygenProductionInventory(date);
        }
        return historyPerDate;
    }
}