package ca.ulaval.glo4002.booking.domain.oxygen2.history;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OxygenHistory {

    private Map<LocalDate, OxygenHistoryEntry> history;

    public OxygenHistory() {
        history = new HashMap<>();
    }

    public List<OxygenHistoryEntry> findAll() {
        return new ArrayList<>(history.values());
    }

    public OxygenHistoryEntry findOrCreate(LocalDate date) {
        OxygenHistoryEntry entry = history.get(date);
        if (entry == null) {
            return new OxygenHistoryEntry(date);
        }
        return entry;
    }

    public void save(OxygenHistoryEntry oxygenHistoryEntry) {
        history.put(oxygenHistoryEntry.getDate(), oxygenHistoryEntry);
    }
}
