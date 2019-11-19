package ca.ulaval.glo4002.booking.domain.oxygen.history;

import java.time.LocalDate;
import java.util.List;

public interface OxygenHistoryRepository {
    public List<OxygenHistoryEntry> findAll();
    public OxygenHistoryEntry findOrCreate(LocalDate date);
    public void save(OxygenHistoryEntry oxygenHistoryEntry);
}
