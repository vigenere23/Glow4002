package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryRepository;

public class InMemoryOxygenHistoryRepository implements OxygenHistoryRepository {

    private Map<LocalDate, OxygenHistoryEntry> history;

    public InMemoryOxygenHistoryRepository() {
        history = new TreeMap<>();
    }

    @Override
    public List<OxygenHistoryEntry> findAll() {
        return new ArrayList<>(history.values());
    }

    @Override
    public OxygenHistoryEntry findOrCreate(OffsetDateTime dateTime) {
        return findOrCreate(dateTime.toLocalDate());
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
    public void add(OxygenHistoryEntry oxygenHistoryEntry) {
        history.put(oxygenHistoryEntry.getDate(), oxygenHistoryEntry);
    }
}
