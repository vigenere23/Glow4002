package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistoryRepository;

public class HeapOxygenHistoryRepository implements OxygenHistoryRepository {

    private Map<LocalDate, OxygenHistoryEntry> history;

    public HeapOxygenHistoryRepository() {
        history = new TreeMap<>();
    }

    @Override
    public List<OxygenHistoryEntry> findAll() {
        return new ArrayList<>(history.values());
    }

    @Override
    public OxygenHistoryEntry findOrCreate(LocalDate date) {
        OxygenHistoryEntry entry = history.get(date);
        if (entry == null) {
            return new OxygenHistoryEntry(date);
        }
        return entry;
    }
    
    @Override
    public void save(OxygenHistoryEntry oxygenHistoryEntry) {
        history.put(oxygenHistoryEntry.getDate(), oxygenHistoryEntry);
    }
}
