package ca.ulaval.glo4002.booking.domain.oxygen.history;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public interface OxygenHistoryRepository {
    List<OxygenHistoryEntry> findAll();
    OxygenHistoryEntry findOrCreate(OffsetDateTime dateTime);
    OxygenHistoryEntry findOrCreate(LocalDate date);
    void add(OxygenHistoryEntry oxygenHistoryEntry);
}
