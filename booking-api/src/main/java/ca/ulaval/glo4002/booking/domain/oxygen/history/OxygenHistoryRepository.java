package ca.ulaval.glo4002.booking.domain.oxygen.history;

import java.time.LocalDate;
import java.util.List;

public interface OxygenHistoryRepository {
    List<OxygenHistoryEntry> findAll();
    OxygenHistoryEntry findOrCreate(LocalDate date);
    void add(OxygenHistoryEntry oxygenHistoryEntry);
}
